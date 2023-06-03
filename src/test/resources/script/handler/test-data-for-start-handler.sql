insert into shelter (id, names) values (100,'test1');
insert into shelter (id, names) values (200,'test2');
insert into shelter (id, names) values (300,'test3');

insert into session (id,selected_shelter_id)  values (100,null);
insert into users (id, chat_id, names, session_id) values (100,100,'test1',100);
insert into session (id,selected_shelter_id,is_first_request)  values (200,null,false);
insert into users (id, chat_id, names, session_id) values (200,200,'test2',200);

