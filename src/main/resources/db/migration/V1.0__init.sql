create table customer
(
    id               SERIAL PRIMARY KEY,
    name             varchar(1024)                       not null,
    company          varchar(1024)                       not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null
);