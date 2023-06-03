--liquibase formatted sql
--changeset velikorodnova:init_tables

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
contact varchar,
session_id bigint not null references session (id)
);

create table shelter_user(
shelter_id bigint not null references shelter (id),
user_id bigint not null references users (id)
);

create table answer(
id bigserial not null primary key,
category varchar not null,
texts varchar not null,
title varchar not null,
shelter_id bigint not null references shelter (id)
);

create table volunteer_contact(
id bigserial not null primary key,
chat_id bigserial not null,
email varchar not null,
full_name varchar not null,
phone varchar not null,
telegram_tag varchar not null,
shelter_id bigint not null references shelter (id)
);

create table probation(
id bigserial not null primary key,
shelter_id bigint not null references shelter (id),
user_id bigint not null references users (id),
pet_name varchar not null,
start_date date not null,
probation_status varchar not null,
volunteer_contact_id bigint not null references volunteer_contact (id),
count_probation_days int not null
);

create table report(
id bigserial not null primary key,
dates date,
photo_path varchar not null,
report varchar not null,
shelter_id bigint not null references shelter (id),
user_id bigint not null references users (id)
);