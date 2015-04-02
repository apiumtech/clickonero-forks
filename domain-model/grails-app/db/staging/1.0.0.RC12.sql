START TRANSACTION;

 -- Autor: Manuel Gomez
 -- Cambio de tipo de dato de la columna transaction_id de Integer a String, el cual fue solicitado por wms
 alter table winbits_staging.guide_carrier_history_status modify transaction_id varchar(255);

COMMIT;