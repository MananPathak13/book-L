create table books (
  id       LONG NOT NULL Primary Key AUTO_INCREMENT,
  title    VARCHAR(128) NOT NULL,
  author   VARCHAR(128) NOT NULL 
);

create table reviews (
  id       LONG NOT NULL Primary Key AUTO_INCREMENT,
  bookId   LONG NOT NULL,	
  text     VARCHAR(1024) NOT NULL UNIQUE
);


alter table reviews
  add constraint book_review_fk foreign key (bookId)
  references books (id);
  
--create table users(
--	username varchar_ignorecase(50) not null primary key,
--	password varchar_ignorecase(500) not null,
--	enabled boolean not null);
--
--create table authorities (
--	username varchar_ignorecase(50) not null,
--	authority varchar_ignorecase(50) not null,
--	constraint fk_authorities_users foreign key (username)
--	references users(username));
--	
--create unique index ix_auth_username 
--	on authorities (username, authority);
 

insert into books (title, author)
values ('The 7 Habits of Highly Effective People', 'Stephen R. Covey');
 
insert into books (title, author)
values ('The Prince', 'Niccolo Machiavelli'); 
 
insert into reviews (text, bookId)
values ('An older book, but still a very good read for priniciple-centered leadership.', 1);
 
insert into reviews (text, bookId)
values ('A very old book that expounds on gaining and keeping power; at any and all costs. It was banned by the Pope in 1559.', 2);
