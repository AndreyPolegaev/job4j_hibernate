-- one-to-many
create table if not exists public.mark
(
    id   serial primary key,
    name varchar(64)

);

-- many-to-one
create table if not exists public.model
(
    id      serial primary key,
    name    varchar(64),
    mark_id int references mark (id)
);

drop table mark_model;
drop table mark;
drop table model;