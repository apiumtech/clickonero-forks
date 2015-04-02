create table migration_status (id bigint not null auto_increment, version bigint not null, description varchar(100), name varchar(25) not null unique, primary key (id)) ENGINE=InnoDB;
create table vertical_partner (vertical_id bigint not null, user_id bigint not null, version bigint not null, status_id bigint not null, primary key (vertical_id, user_id)) ENGINE=InnoDB;
alter table vertical_partner add index FK4B3D14FF6F4B0AA9 (user_id), add constraint FK4B3D14FF6F4B0AA9 foreign key (user_id) references user (id);
alter table vertical_partner add index FK4B3D14FFF2C329B9 (status_id), add constraint FK4B3D14FFF2C329B9 foreign key (status_id) references migration_status (id);
alter table vertical_partner add index FK4B3D14FF98AFE749 (vertical_id), add constraint FK4B3D14FF98AFE749 foreign key (vertical_id) references vertical (id);
