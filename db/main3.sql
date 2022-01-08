-- one-to-many
create table if not exists h3.mar
(
    id   serial primary key,
    name varchar(64)

);

-- many-to-one
create table if not exists h3.mod
(
    id      serial primary key,
    name    varchar(64),
    mark_id int references h3.mar(id)
);

create schema h3;

drop table h3.mod;
drop table h3.mar;
