insert into shelter (id, names) values (100,'test1');

insert into answer (id,category, texts, title, shelter_id) values (100,'INFORMATION','text1','title1',100);
insert into answer (id,category, texts, title, shelter_id) values (200,'INFORMATION','text2','title2',100);

insert into session (id,selected_shelter_id)  values (100,100);
insert into users (id, chat_id, names, session_id) values (100,100,'test1',100);

