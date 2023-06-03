insert into shelter (id, names) values (100,'test1');
insert into shelter (id, names) values (200,'test2');

insert into session (id,selected_shelter_id)  values (100,null);
insert into users (id, chat_id, names, session_id) values (100,100,'test1',100);