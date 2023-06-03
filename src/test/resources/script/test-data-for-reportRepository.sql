insert into session (id, selected_shelter_id)
values (100, null);
insert into session (id, selected_shelter_id)
values (200, null);
insert into users (id, chat_id, names, session_id)
VALUES (100, 100, 'test', 100);
insert into users (id, chat_id, names, session_id)
VALUES (200, 200, 'test1', 200);
insert into shelter (id, names)
values (100, 'test');
insert into shelter (id, names)
values (200, 'test');

insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (100, '2022-01-11', 'test', 100, 100,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (200, '2022-01-12', 'test', 100, 100,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (300, '2022-01-13', 'test', 100, 100,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (400, '2022-01-14', 'test', 100, 100,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (5, '2022-01-11', 'test', 200, 200,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (600, '2022-01-12', 'test', 200, 200,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (700, '2022-01-13', 'test', 200, 200,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (800, '2022-01-14', 'test', 200, 200,'test');
insert into report (id, dates, report, shelter_id, user_id,photo_path)
values (900, '2022-01-15', 'test', 200, 200,'test');