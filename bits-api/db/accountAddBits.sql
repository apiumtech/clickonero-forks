-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DROP PROCEDURE  IF EXISTS accountAddBits;

DELIMITER $$

CREATE PROCEDURE `accountAddBits`(IN accountId INT, IN winbits DECIMAL(19,2), 
        IN concept varchar(255), IN activation_date datetime, IN expiration_date datetime,
        OUT balanceRes DECIMAL(19,2), OUT success BIT, OUT currentDepositId BIGINT(20))
BEGIN
    DECLARE currentTransactionId BIGINT(20);
    DECLARE newBalance decimal(19,2) DEFAULT 0; 
    DECLARE currentBalance decimal(19,2) DEFAULT 0; 
    DECLARE newActive BOOL DEFAULT true;

    START TRANSACTION;
        SET success = 0;

        #SELECT activation_date as `fechaActivacion`;

        IF (activation_date IS NULL OR activation_date <= now()) THEN
            SELECT balance INTO currentBalance FROM account WHERE id = accountId FOR UPDATE;
            SET newBalance = winbits + currentBalance;
        ELSE
            SELECT balance INTO newBalance FROM account WHERE id = accountId ;
            SET newActive = false;
        END IF;

        SELECT newBalance  AS `balance`; 

        INSERT transaction(account_id, amount, balance, concept, date_created, last_updated, version)
            VALUES(accountId, winbits, newBalance, concept, NOW(), NOW(), 0);
        SELECT LAST_INSERT_ID() INTO currentTransactionId;

        INSERT deposit(amount, activation_date, expiration_date, date_created, last_updated,active, version, opening_balance, account_id, transaction_id)
            VALUES(winbits, activation_date, expiration_date, NOW(), NOW(), newActive, 0, amount, accountId, currentTransactionId);
        SELECT LAST_INSERT_ID() INTO currentDepositId ;
        
	IF(activation_date IS NULL OR activation_date <= now()) THEN
            UPDATE account SET balance = newBalance WHERE id = accountId;
        END IF;

        SET balanceRes = newBalance;
        SET success = 1;
    COMMIT;

END$$

DELIMITER ;
