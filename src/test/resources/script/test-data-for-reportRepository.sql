insert into session (id, selected_shelter_id)
values (1, null);
insert into session (id, selected_shelter_id)
values (2, null);
insert into users (id, chat_id, name, session_id)
VALUES (1, 1, 'test', 1);
insert into users (id, chat_id, name, session_id)
VALUES (2, 2, 'test1', 2);
insert into shelter (id, name)
values (1, 'test');
insert into shelter (id, name)
values (2, 'test');

insert into volunteer_contact (id, email, full_name, telegram_tag, phone, shelter_id, chat_id)
values (1,'v1@mail.ru','test','test','test',1,10);

insert into volunteer_contact (id, email, full_name, telegram_tag, phone, shelter_id, chat_id)
values (2,'v2@mail.ru','test','test','test',2,20);

insert into probation (id,count_probation_days, pet_name, start_date, shelter_id, user_id, probation_status, volunteer_contact_id)
values (1,10,'test','2022-01-10',1,1,'APPOINTED',1);

insert into probation (id,count_probation_days, pet_name, start_date, shelter_id, user_id, probation_status, volunteer_contact_id)
values (2,10,'test','2022-01-10',2,1,'APPOINTED',2);

insert into report (id, date, report,probation_id)
values (1, '2022-01-11', 'test',1);
insert into report (id, date, report,probation_id)
values (2, '2022-01-12', 'test',1);
insert into report (id, date, report,probation_id)
values (3, '2022-01-13', 'test',1);
insert into report (id, date, report,probation_id)
values (4, '2022-01-14', 'test',1);
insert into report (id, date, report,probation_id)
values (5, '2022-01-11', 'test',2);
insert into report (id, date, report,probation_id)
values (6, '2022-01-12', 'test',2);
insert into report (id, date, report,probation_id)
values (7, '2022-01-13', 'test',2);
insert into report (id, date, report,probation_id)
values (8, '2022-01-14', 'test',2);
insert into report (id, date, report,probation_id)
values (9, '2022-01-15', 'test',2);