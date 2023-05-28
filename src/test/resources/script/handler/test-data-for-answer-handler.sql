insert into shelter (id, name) values (1,'test1');

insert into answer (id,category, text, title, shelter_id) values (1,'INFORMATION','text1','title1',1);
insert into answer (id,category, text, title, shelter_id) values (2,'INFORMATION','text2','title2',1);
insert into answer (id,category, text, title, shelter_id) values (3,'GETTING_ANIMAL','text3','title3',1);

insert into session (id,selected_shelter_id)  values (1,1);
insert into users (id, chat_id, name, session_id) values (1,1,'test1',1);

