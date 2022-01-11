create table if not exists h4.candidate
(
    id         serial primary key,
    name       varchar(64),
    experience text,
    salary     int,
    vacancyBase_id int unique references h4.vacancyBase(id)
);

create table if not exists h4.vacancyBase
(
    id         serial primary key,
    name       varchar(64)
);

create table if not exists h4.vacancy
(
    id         serial primary key,
    name       text,
    vacancyBase_id int references h4.vacancyBase(id)
);