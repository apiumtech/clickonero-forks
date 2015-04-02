-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DROP PROCEDURE  IF EXISTS accountUpdateBalance;

DELIMITER $$

CREATE PROCEDURE `accountUpdateBalance`(OUT balanceRes DECIMAL(19,2))
BEGIN
    DECLARE currentDepositId BIGINT(20);
    DECLARE currentTransactionId BIGINT(20);
    DECLARE depositoId BIGINT(20);
    DECLARE accountId BIGINT(20);
    DECLARE newBalance decimal(19,2) DEFAULT 0; 
    DECLARE currentBalance decimal(19,2) DEFAULT 0; 
    DECLARE deposit_amount decimal(19,2) DEFAULT 0; 
    DECLARE no_more_rows BOOLEAN;
    DECLARE loop_cntr INT DEFAULT 0;
    DECLARE num_rows INT DEFAULT 0;
    DECLARE success BIT(1) DEFAULT 0;
    
    DECLARE depositos_cur CURSOR FOR
        SELECT d.id, d.amount, d.account_id     
        FROM deposit d     
        WHERE d.active = false AND  d.amount > 0 
	      AND activation_date > date_add( curdate(), interval - 1 day )   
	      AND activation_date < date_add(curdate(), interval + 1 day) 
        ORDER BY account_id;
    

    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    DECLARE CONTINUE HANDLER FOR NOT FOUND
    SET no_more_rows = TRUE;


    START TRANSACTION;
        SET success = 0;
        OPEN depositos_cur;
        SELECT FOUND_ROWS() INTO num_rows;



    withdraw_loop: LOOP
    FETCH depositos_cur
    INTO depositoId,  deposit_amount, accountId;

        SELECT balance INTO currentBalance FROM account WHERE id = accountId FOR UPDATE;
        SET newBalance = currentBalance + deposit_amount;

        UPDATE deposit set active = true where deposit.id = depositoId;
        UPDATE account set  balance = newBalance where accountId = account.id;

        IF ( no_more_rows ) THEN
            CLOSE depositos_cur;
            LEAVE withdraw_loop;
        END IF;


    END LOOP withdraw_loop;
    SET success = 1;
    COMMIT;

END$$

DELIMITER ;
