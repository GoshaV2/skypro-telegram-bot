insert into shelter (id, name) values (1,'test1');
insert into shelter (id, name) values (2,'test2');
insert into shelter (id, name) values (3,'test3');

insert into session (id,selected_shelter_id)  values (1,null);
insert into users (id, chat_id, name, session_id) values (1,1,'test1',1);
insert into session (id,selected_shelter_id,is_first_request)  values (2,null,false);
insert into users (id, chat_id, name, session_id) values (2,2,'test2',2);

