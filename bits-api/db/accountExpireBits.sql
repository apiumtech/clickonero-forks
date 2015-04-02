DROP PROCEDURE IF EXISTS accountExpireBits;

DELIMITER $$

CREATE PROCEDURE `accountExpireBits`(
	IN accountSourceId BIGINT(20),
	IN accountTargetId BIGINT(20),
	IN concept VARCHAR(255),
	IN depositId BIGINT(20),
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
	DECLARE sourceTransactionId BIGINT(20);
	DECLARE targetTransactionId BIGINT(20);
  DECLARE depositTargetId BIGINT(20);
  DECLARE winbits DECIMAL(19,2) DEFAULT 0;

	START TRANSACTION;

	SELECT amount INTO winbits
	       FROM deposit
	       where id = depositId;

  IF winbits > 0 THEN

      SELECT balance INTO currentSourceBalance
             FROM account
             WHERE id = accountSourceId FOR UPDATE;
      SELECT balance INTO currentTargetBalance
             FROM account
             WHERE id = accountTargetId FOR UPDATE;


      SET newSourceBalance = currentSourceBalance - winbits;
      SET newTargetBalance = currentTargetBalance + winbits;


      INSERT transaction(account_id, amount, balance, concept,
                         date_created, last_updated, version)
             VALUES (accountSourceId, -(winbits), newSourceBalance,
                     concept, now(), now(), 0);
      SELECT LAST_INSERT_ID() INTO sourceTransactionId;

      INSERT transaction(account_id, amount, balance, concept, date_created, last_updated, version)
             VALUES (accountTargetId, winbits, newTargetBalance, concept, now(), now(), 0);
      SELECT LAST_INSERT_ID() INTO targetTransactionId;

      INSERT deposit(amount, activation_date, expiration_date, date_created, last_updated,
                    active, version, opening_balance, account_id, transaction_id)
             VALUES (winbits, activation_date, expiration_date, now(), now(),
                    true, 0, winbits, accountTargetId, targetTransactionId);
      SELECT LAST_INSERT_ID() INTO depositTargetId;

      INSERT withdrawal(amount, account_id, deposit_id, transaction_id, date_created,
                        last_updated, version, refunded, target_id)
              VALUES(winbits, accountSourceId, depositId, sourceTransactionId, now(),
                        now(), 0, false, depositTargetId);

      UPDATE deposit SET amount = 0 WHERE id = depositId;

      SET balanceSource = newSourceBalance;
      SET balanceTarget = newTargetBalance;
      SET transactionWithdrawId = sourceTransactionId;
      SET transactionDepositId = targetTransactionId;
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




