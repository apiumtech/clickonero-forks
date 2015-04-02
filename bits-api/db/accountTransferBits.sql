DROP PROCEDURE IF EXISTS accountTransferBits;

DELIMITER $$

CREATE PROCEDURE `accountTransferBits`(
	IN accountSourceId BIGINT(20), 
	IN accountTargetId BIGINT(20),
	IN winbits DECIMAL(19,2),
	IN concept VARCHAR(255),
	IN activation_date datetime,
	IN expiration_date datetime,
	OUT balanceSource DECIMAL(19, 2),
	OUT balanceTarget DECIMAL(19, 2),
	OUT transactionWithdrawId BIGINT(20),
        OUT transactionDepositId BIGINT(20),
        OUT success BIT)

BEGIN
	DECLARE currentSourceBalance DECIMAL(19,2) DEFAULT 0;
	DECLARE currentTargetBalance DECIMAL(19,2) DEFAULT 0;
	DECLARE newSourceBalance DECIMAL(19,2) DEFAULT 0;
	DECLARE newTargetBalance DECIMAL(19,2) DEFAULT 0;
	DECLARE enoughBalance BOOLEAN DEFAULT TRUE;
	DECLARE sourceTransactionId BIGINT(20);
	DECLARE targetTransactionId BIGINT(20);
        DECLARE depositId BIGINT(20);
	DECLARE deposit_amount DECIMAL(19, 2) DEFAULT 0;
	DECLARE deposit_amount_new DECIMAL(19,2) DEFAULT 0;
        DECLARE depositTargetId BIGINT(20);

	DECLARE no_more_rows BOOLEAN;
	DECLARE loop_cntr INT DEFAULT 0;
	DECLARE num_rows INT DEFAULT 0;

	DECLARE deposits_cur CURSOR FOR
	Select d.id, d.amount 
	   from deposit d 
	   WHERE d.account_id = accountSourceId AND d.amount > 0
	         AND d.activation_date <= now()
	   ORDER BY d.expiration_date;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND

	START TRANSACTION;

	SELECT balance INTO currentSourceBalance 
	       FROM account 
	       WHERE id = accountSourceId FOR UPDATE;
	SELECT balance INTO currentTargetBalance
	       FROM account
	       WHERE id = accountTargetId FOR UPDATE;

	
	SET newSourceBalance = currentSourceBalance - winbits;
	SET newTargetBalance = currentTargetBalance + winbits;
	IF newSourceBalance < 0 THEN
		SET enoughBalance = FALSE;
	END IF;

	OPEN deposits_cur;
	SELECT FOUND_ROWS() INTO num_rows;

        INSERT transaction(account_id, amount, balance, concept, 
		              date_created, last_updated, version) 
	       VALUES (accountSourceId, -(winbits), newSourceBalance, 
		              concept, now(), now(), 0);
        SELECT LAST_INSERT_ID() INTO sourceTransactionId;

	INSERT transaction(account_id, amount, balance, concept,
	                      date_created, last_updated, version)
	       VALUES (accountTargetId, winbits, newTargetBalance, 
	                      concept, now(), now(), 0);	
        SELECT LAST_INSERT_ID() INTO targetTransactionId;

	withdraw_loop: LOOP
	FETCH deposits_cur
        INTO depositId, deposit_amount;

	  IF (not enoughBalance OR no_more_rows OR winbits = 0) THEN
		CLOSE deposits_cur;
		LEAVE withdraw_loop;
	  END IF;

          IF (winbits - deposit_amount <= 0) THEN
		SET deposit_amount_new = winbits;
		SET winbits = 0;
	  ELSE
		SET deposit_amount_new = deposit_amount;
		SET winbits = winbits - deposit_amount;
	  END IF;

	  INSERT deposit(amount, 
		         activation_date, 
			 expiration_date, 
			 date_created,
	                 last_updated, 
			 active, 
			 version, 
			 opening_balance, 
			 account_id, 
			 transaction_id)
          VALUES (deposit_amount_new, 
		  activation_date, 
		  expiration_date, 
		  now(),
	          now(), 
		  true, 
		  0, 
		  deposit_amount_new, 
		  accountTargetId, 
		  targetTransactionId);
	  SELECT LAST_INSERT_ID() INTO depositTargetId;

	  INSERT withdrawal(amount, 
		            account_id, 
			    deposit_id, 
			    transaction_id, 
		            date_created, 
			    last_updated, 
			    version, 
			    refunded,
		            target_id)
	    VALUES(deposit_amount_new,
	           accountSourceId,
	           depositId,
	           sourceTransactionId,
	           now(),
	           now(),
	           0,
	           false,
	           depositTargetId);
		
          UPDATE deposit SET amount = amount - deposit_amount_new 
            WHERE id = depositId;

          SET loop_cntr = loop_cntr + 1;
 
	END LOOP withdraw_loop;
     
	IF winbits != 0 THEN
		SET enoughBalance = FALSE;
	END IF;

	SET balanceSource = newSourceBalance;
	SET balanceTarget = newTargetBalance;
        SET transactionWithdrawId = sourceTransactionId;
	SET transactionDepositId = targetTransactionId;

	IF enoughBalance THEN
		SET success = 1;
		UPDATE account SET balance = newSourceBalance where id = accountSourceId;
		UPDATE account SET balance = newTargetBalance where id = accountTargetId;
		COMMIT;
	ELSE
		SET success = 0;
		ROLLBACK;
	END IF;
END$$

DELIMITER ;




