create table if not exists h4.candidate (
    id serial primary key,
    name varchar(64),
    experience text,
    salary int
);

create schema h4;