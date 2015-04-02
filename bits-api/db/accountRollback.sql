DROP PROCEDURE IF EXISTS accountRollback;

DELIMITER $$

CREATE PROCEDURE `accountRollback`(
       IN transactionId BIGINT(20),
       IN concept VARCHAR(255),
       IN expiredOffset INT,
       OUT targetBalance DECIMAL(19,2),
       OUT success BIT)
     

BEGIN
	DECLARE accountTargetId BIGINT(20);
        DECLARE accountSalesId BIGINT(20);
        DECLARE currentSalesBalance DECIMAL(19,2) DEFAULT 0;
        DECLARE currentTargetBalance DECIMAL(19,2) DEFAULT 0;
        DECLARE newSalesBalance DECIMAL(19,2) DEFAULT 0;
	DECLARE newTargetBalance DECIMAL(19,2) DEFAULT 0;
	DECLARE salesTransactionId BIGINT(20);
	DECLARE targetTransactionId BIGINT(20);
	DECLARE winbits DECIMAL(19,2) DEFAULT 0;
        DECLARE withdrawId BIGINT(20);
        DECLARE withdrawAmount DECIMAL(19,2) DEFAULT 0;
	DECLARE depositTargetId BIGINT(20);
	DECLARE depositId BIGINT(20);
	DECLARE currentExpirationDate datetime;
	DECLARE currentDateCreated datetime;
	DECLARE newExpiredOffset INT;

	DECLARE num_rows INT DEFAULT 0;
	DECLARE no_more_rows BOOLEAN;
	DECLARE loop_cntr INT DEFAULT 0;

	DECLARE withdrawals_cur CURSOR FOR
	SELECT w.id, w.amount, w.target_id, w.date_created, d.expiration_date from withdrawal w 
	        left join deposit as d on d.id = w.deposit_id
	        where 
		w.transaction_id = transactionId AND refunded = false FOR UPDATE; 
        	
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

	DECLARE CONTINUE HANDLER FOR NOT FOUND

	START TRANSACTION;

	SELECT d.account_id INTO accountSalesId FROM withdrawal w 
	       left join deposit d on d.id = w.target_id 
	       where w.transaction_id = transactionId group by d.account_id; 

        SELECT account_id, amount INTO accountTargetId, winbits FROM transaction
	       WHERE id = transactionId;

	SELECT balance INTO currentSalesBalance FROM account
	       WHERE id = accountSalesId FOR UPDATE;
	SELECT balance INTO currentTargetBalance FROM account
	       WHERE id = accountTargetId FOR UPDATE;

        SET newSalesBalance = currentSalesBalance + winbits;
	SET newTargetBalance = currentTargetBalance - winbits;
        

	OPEN withdrawals_cur;
	SELECT FOUND_ROWS() INTO num_rows;
        
	IF (num_rows) > 0 THEN
          INSERT transaction(account_id, amount, balance, concept, 
		              date_created, last_updated, version) 
	       VALUES (accountSalesId, winbits, newSalesBalance, 
		              concept, now(), now(), 0);
          SELECT LAST_INSERT_ID() INTO salesTransactionId;

	  INSERT transaction(account_id, amount, balance, concept,
	                      date_created, last_updated, version)
	       VALUES (accountTargetId, -(winbits), newTargetBalance, 
	                      concept, now(), now(), 0);	
          SELECT LAST_INSERT_ID() INTO targetTransactionId;

          rollback_loop: LOOP
	  FETCH withdrawals_cur 
	  INTO withdrawId, withdrawAmount, depositId, currentDateCreated, currentExpirationDate;
        	
		IF(loop_cntr >= num_rows) THEN
			CLOSE withdrawals_cur;
			LEAVE rollback_loop;
		END IF;
                
		
                SET newExpiredOffset = DATEDIFF(currentExpirationDate, currentDateCreated) + expiredOffset;

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
          	VALUES (withdrawAmount, 
		  	now(), 
		  	date_add(curdate(), interval + newExpiredOffset day), 
		  	now(),
	          	now(), 
		  	true, 
		  	0, 
		  	withdrawAmount, 
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
	    	VALUES(withdrawAmount,
	           	accountSalesId,
	           	depositId,
	           	salesTransactionId,
	           	now(),
	           	now(),
	           	0,
	           	false,
	           	depositTargetId);
		
          	UPDATE deposit SET amount = amount - withdrawAmount
            	WHERE id = depositId;

		UPDATE withdrawal SET refunded = true WHERE id = withdrawId;

                SET loop_cntr = loop_cntr + 1;
 
	  END LOOP rollback_loop;

	  SET success = 1;
	  SET targetBalance = newTargetBalance;
	  UPDATE account SET balance = newSalesBalance where id = accountSalesId;
	  UPDATE account SET balance = newTargetBalance where id = accountTargetId;
	  COMMIT;
        ELSE
	  SET success = 0;
	  ROLLBACK; 
        END IF;

END$$

DELIMITER ;


