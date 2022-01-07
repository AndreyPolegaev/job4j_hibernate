
-- many-to-many
create table if not exists h2.author
(
    id serial primary key,
    name varchar(64)
);

-- many-to-many
create table if not exists h2.book
(
    id serial primary key,
    name varchar(64)
);

create schema h2;

drop table author_book;
drop table h2.author;
drop table h2.book;
