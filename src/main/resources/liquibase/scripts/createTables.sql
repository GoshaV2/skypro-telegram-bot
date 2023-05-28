--liquibase formatted sql
--changeset velikorodnova:1

create table shelter(
id bigserial not null primary key,
names varchar not null
);

create table session(
id bigserial not null primary key,
selected_shelter_id bigint references shelter (id),
has_waiting_contact boolean default false,
is_report_sending boolean default false,
is_first_request boolean default true
);

create table users (
id bigserial not null primary key,
chat_id bigint not null,
names varchar not null,
contact varchar not null,
session_id bigint references session (id)
);

create table shelter_user(
shelter_id bigint references shelter (id),
user_id bigint references users (id)
);

create table answer(
id bigserial not null primary key,
category varchar not null,
command varchar not null,
texts varchar not null,
title varchar not null,
shelter_id bigint references shelter (id)
);

create table probation(
id bigserial not null primary key,
count_probation_days bigint not null,
pet_name varchar not null,
start_date date,
shelter_id bigint references shelter (id),
user_id bigint references users (id)
);

create table report(
id bigserial not null primary key,
dates date,
photo bytea,
report varchar not null,
shelter_id bigint references shelter (id),
user_id bigint references users (id)
);

create table volunteer_contact(
id bigserial not null primary key,
email varchar not null,
full_name varchar not null,
phone varchar not null,
telegram_tag varchar not null,
shelter_id bigint references shelter (id)
);