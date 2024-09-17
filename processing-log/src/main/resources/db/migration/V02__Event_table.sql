create table account_event
(
    uuid varchar,
    user_id int not null ,
    account_id int,
    operation smallint not null ,
    amount numeric not null ,
    created timestamp not null,
    primary key(account_id,uuid)
);