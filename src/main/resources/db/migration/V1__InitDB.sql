create table answer (answer_id bigint not null auto_increment, text varchar(255) not null, question_id bigint, primary key (answer_id)) engine=InnoDB;
create table poll (poll_id bigint not null auto_increment, created datetime, expiration_date datetime, is_active bit not null, description varchar(255), name varchar(255) not null, primary
key (poll_id)) engine=InnoDB;
create table question (question_id bigint not null auto_increment, is_active bit not null, body varchar(255) not null, type varchar(255) not null, poll_id bigint, primary key (question_id)) engine=InnoDB;
create table role (role_id bigint not null auto_increment, role_name varchar(255), primary key (role_id)) engine=InnoDB;
create table user (user_id bigint not null, is_active bit not null, password varchar(255) not null, username varchar(255) not null, primary key (user_id)) engine=InnoDB;
create table user_answer (user_answer_id bigint not null auto_increment, question_body varchar(255), user_answer_body varchar(255), question_id bigint, user_poll_answers_id bigint, primary
key (user_answer_id)) engine=InnoDB;
create table user_authority (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;
create table user_poll_answers (user_poll_answers_id bigint not null auto_increment, final_date datetime, poll_id bigint, user_polls_id bigint, primary key (user_poll_answers_id)) engine=InnoDB;
create table user_polls (user_polls_id bigint not null auto_increment, user_unique_id bigint not null, primary key (user_polls_id)) engine=InnoDB;
create table user_seq (next_val bigint) engine=InnoDB;
insert into user_seq values ( 1 );



alter table role add constraint UK_epk9im9l9q67xmwi4hbed25do unique (role_name);
alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table user_polls add constraint UK_tfyqxft7dxny92odp6m2h5w8e unique (user_unique_id);
alter table answer add constraint FK8frr4bcabmmeyyu60qt7iiblo foreign key (question_id) references question (question_id);
alter table question add constraint FKs9uu0i9ipek2tnqty4ohdek27 foreign key (poll_id) references poll (poll_id);
alter table user_answer add constraint FKpsk90eok3ounaet92hku3gny1 foreign key (question_id) references question (question_id);
alter table user_answer add constraint FKaawctkxsjf2mx0gbcbbd7t7au foreign key (user_poll_answers_id) references user_poll_answers (user_poll_answers_id);
alter table user_authority add constraint FKash3fy9hdayq3o73fir11n3th foreign key (role_id) references role (role_id);
alter table user_authority add constraint FKpqlsjpkybgos9w2svcri7j8xy foreign key (user_id) references user (user_id);
alter table user_poll_answers add constraint FKmxqi08nc7wai121fjkmoyr68l foreign key (poll_id) references poll (poll_id);
alter table user_poll_answers add constraint FK30uo1wrdld88hplyb2dtay7lr foreign key (user_polls_id) references user_polls (user_polls_id);