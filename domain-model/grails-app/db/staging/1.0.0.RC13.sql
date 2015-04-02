START TRANSACTION;

alter table winbits_staging.sku_outcome_extra add odc_detail_id bigint;
alter table winbits_staging.sku_outcome_extra add index FK512991418C1208CB (odc_detail_id), add constraint FK512991418C1208CB foreign key (odc_detail_id) references winbits_staging.odc_detail (id);

create table  winbits_staging.registration_code (id bigint not null auto_increment, date_created datetime not null, token varchar(255) not null, username varchar(255) not null, primary key (id)) ENGINE=InnoDB;
create table  winbits_staging.admin_role_admin_requestmap (admin_role_id bigint not null, admin_requestmap_id bigint not null, primary key (admin_role_id, admin_requestmap_id)) ENGINE=InnoDB;
create table  winbits_staging.department (id bigint not null auto_increment, version bigint not null, description varchar(100), name varchar(25) not null unique, primary key (id)) ENGINE=InnoDB;
create table  winbits_staging.sales_agent (id bigint not null auto_increment, admin_user_id bigint not null, api_token varchar(255), enabled bit not null, sales_charge integer, primary key (id)) ENGINE=InnoDB;

ALTER TABLE winbits_staging.admin_user CHANGE COLUMN email username VARCHAR(45) NULL DEFAULT NULL;

ALTER TABLE winbits_staging.admin_user ADD name VARCHAR(255) NOT NULL;

ALTER TABLE winbits_staging.admin_user ADD last_name VARCHAR(255) NOT NULL;

ALTER TABLE winbits_staging.admin_user ADD department_id BIGINT(20) NULL DEFAULT NULL;

ALTER TABLE winbits_staging.admin_user ADD last_login_date DATETIME NULL DEFAULT NULL;

ALTER TABLE winbits_staging.admin_user ADD date_created DATETIME NULL DEFAULT NULL;

ALTER TABLE winbits_staging.admin_user ADD last_updated DATETIME NULL DEFAULT NULL;

alter table  winbits_staging.admin_role_admin_requestmap add index FK51135B1615F72FC9 (admin_requestmap_id), add constraint FK51135B1615F72FC9 foreign key (admin_requestmap_id) references winbits_staging.admin_requestmap (id);

alter table  winbits_staging.admin_role_admin_requestmap add index FK51135B1612FC1669 (admin_role_id), add constraint FK51135B1612FC1669 foreign key (admin_role_id) references winbits_staging.admin_role (id);

alter table  winbits_staging.admin_user add index FK29045ABBA6B3362C (department_id), add constraint FK29045ABBA6B3362C foreign key (department_id) references winbits_staging.department (id);

alter table  winbits_staging.sales_agent add index FK6655F612B826DA49 (admin_user_id), add constraint FK6655F612B826DA49 foreign key (admin_user_id) references winbits_staging.admin_user (id);

ALTER TABLE  winbits_staging.orders ADD sales_agent_id BIGINT(20) NULL DEFAULT NULL;

ALTER TABLE  winbits_staging.admin_requestmap ADD description VARCHAR(255) NOT NULL;

ALTER TABLE winbits_staging.admin_role ADD description VARCHAR(255) NOT NULL;

INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'IS_AUTHENTICATED_ANONYMOUSLY', 'Acceso a raíz', '/**');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'IS_AUTHENTICATED_FULLY', 'Acceso a raíz', '/monitoring/**');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER', 'Acceso a switch user winbits', '/switchuser/switchUser');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/create');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/save');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/update');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/edit');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/view');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/search');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/delete');
INSERT INTO winbits_staging.admin_requestmap (version, config_attribute, description, url) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Acceso a switch admin user winbits', '/switchuser/remove');


INSERT INTO winbits_staging.admin_role (version, authority, description) VALUES ('0', 'ROLE_SWITCH_USER_ADMIN', 'Rol del administrador de switch user');
INSERT INTO winbits_staging.admin_role (version, authority, description) VALUES ('0', 'ROLE_SWITCH_USER', 'Rol de usuario switch user');
INSERT INTO winbits_staging.admin_role (version, authority, description) VALUES ('0', 'ROLE_ROOT', 'Rol del Administrador de Winbits');
INSERT INTO winbits_staging.admin_role (version, authority, description) VALUES ('0', 'ROLE_VERTICAL', 'Rol de vertical');

COMMIT;

