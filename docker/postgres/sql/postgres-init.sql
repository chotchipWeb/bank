create table Account(
                        id int primary key generated by default as identity,
                        user_id int not null,
                        currency_code varchar(3) not null,
                        balance numeric    not null
);