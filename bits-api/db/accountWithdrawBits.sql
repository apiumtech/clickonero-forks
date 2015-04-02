-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DROP PROCEDURE  IF EXISTS accountWithdrawBits;

DELIMITER $$

CREATE PROCEDURE `accountWithdrawBits`(IN accountId INT, IN winbits DECIMAL(19,2),
    IN concept varchar(255),
    OUT balanceRes DECIMAL(19,2), OUT transactionId BIGINT(20), OUT success BIT)
BEGIN
    DECLARE depositoId BIGINT(20);
    DECLARE currentTransactionId BIGINT(20);
    DECLARE newBalance decimal(19,2) DEFAULT 0;
    DECLARE currentBalance decimal(19,2) DEFAULT 0;
    DECLARE deposit_amount decimal(19,2) DEFAULT 0;
    DECLARE deposit_amount_new  decimal(19,2) DEFAULT 0;
    DECLARE puede_actualizar BOOLEAN DEFAULT TRUE;

    DECLARE no_more_rows BOOLEAN;
    DECLARE loop_cntr INT DEFAULT 0;
    DECLARE num_rows INT DEFAULT 0;

    DECLARE depositos_cur CURSOR FOR
    SELECT d.id, d.amount
    FROM deposit d INNER JOIN transaction t ON (t.deposit_id = d.id)
    WHERE t.account_id = accountId AND d.amount > 0 AND activation_date <= now()  ORDER BY isnull(expiration_date), expiration_date;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    DECLARE CONTINUE HANDLER FOR NOT FOUND
    SET no_more_rows = TRUE;

    START TRANSACTION;

    SET success = 0;
    SET transactionId = NULL;

    SELECT balance INTO currentBalance FROM account WHERE id = accountId FOR UPDATE;
    SET newBalance = currentBalance - winbits ;
    IF newBalance < 0 THEN
        SET puede_actualizar = FALSE;
    END IF;

    #SELECT newBalance, currentBalance, puede_actualizar;

    OPEN depositos_cur;
    SELECT FOUND_ROWS() INTO num_rows;

    #SELECT num_rows;

    INSERT transaction(account_id, amount, balance, concept, date_created, last_updated, version)
    VALUES(accountId, -(winbits), newBalance, concept, NOW(), NOW(), 0);
    SELECT LAST_INSERT_ID() INTO currentTransactionId ;

    withdraw_loop: LOOP
    FETCH depositos_cur
    INTO depositoId,  deposit_amount;

        #SELECT depositoId, winbits, deposit_amount, no_more_rows;

        IF (not puede_actualizar OR no_more_rows OR  winbits = 0 OR newBalance < 0) THEN
            CLOSE depositos_cur;
            LEAVE withdraw_loop;
        END IF;

        IF (winbits - deposit_amount <= 0) THEN
            #SELECT winbits as 'if';
            SET deposit_amount_new =winbits;
            #SELECT deposit_amount_new;
            SET winbits = 0;
            #SELECT deposit_amount_new;
        ELSE
            #SELECT winbits;
            SET deposit_amount_new = deposit_amount;
            SET winbits = winbits - deposit_amount;
            #SELECT deposit_amount_new;
        END IF;


        INSERT withdrawal(amount, account_id, deposit_id, transaction_id, date_created, last_updated, version, refunded)
        VALUES(deposit_amount_new, accountId, depositoId, currentTransactionId, NOW(), NOW(), 0, false);

        UPDATE deposit SET amount = amount - deposit_amount_new WHERE id = depositoId;

        SET loop_cntr = loop_cntr + 1;
        #SELECT loop_cntr;

    END LOOP withdraw_loop;

    IF winbits != 0 THEN
      SET puede_actualizar = FALSE;
    END IF;

    SET balanceRes = newBalance;
    SET success = 1;
    IF puede_actualizar THEN
      UPDATE account SET balance = newBalance where id = accountId;
    END IF;
    SELECT IF(puede_actualizar,1,0) INTO success;
    IF puede_actualizar THEN
      SET transactionId = currentTransactionId;
      COMMIT;
    ELSE
      ROLLBACK;
    END IF;
END$$

DELIMITER ;
