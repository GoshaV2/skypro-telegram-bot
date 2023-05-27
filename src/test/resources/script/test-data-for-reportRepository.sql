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

insert into report (id, date, report, shelter_id, user_id)
values (1, '2022-01-11', 'test', 1, 1);
insert into report (id, date, report, shelter_id, user_id)
values (2, '2022-01-12', 'test', 1, 1);
insert into report (id, date, report, shelter_id, user_id)
values (3, '2022-01-13', 'test', 1, 1);
insert into report (id, date, report, shelter_id, user_id)
values (4, '2022-01-14', 'test', 1, 1);
insert into report (id, date, report, shelter_id, user_id)
values (5, '2022-01-11', 'test', 2, 2);
insert into report (id, date, report, shelter_id, user_id)
values (6, '2022-01-12', 'test', 2, 2);
insert into report (id, date, report, shelter_id, user_id)
values (7, '2022-01-13', 'test', 2, 2);
insert into report (id, date, report, shelter_id, user_id)
values (8, '2022-01-14', 'test', 2, 2);
insert into report (id, date, report, shelter_id, user_id)
values (9, '2022-01-15', 'test', 2, 2);