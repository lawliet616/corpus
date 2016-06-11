DROP TABLE CORPUS.registered_user cascade constraints;
DROP TABLE CORPUS.taken_course cascade constraints;
DROP TABLE CORPUS.course cascade constraints;
DROP TABLE CORPUS.password_settings cascade constraints;
DROP TABLE CORPUS.message cascade constraints;
DROP TABLE CORPUS.inbox cascade constraints;

DROP SEQUENCE CORPUS.registered_user_seq;
DROP SEQUENCE CORPUS.course_seq;
DROP SEQUENCE CORPUS.message_seq;

CREATE TABLE CORPUS.registered_user (
	id number(19,0) not null,
	username varchar2(255 char) not null,
	password varchar2(255 char) not null,
	full_name varchar2(255 char),
	email varchar2(255 char),
	role int not null,
	primary key (id),
	unique (username),
  unique (email)
);
	
CREATE TABLE CORPUS.course (
	id number(19,0) not null,
	name varchar2(255 char) not null,
	room varchar2(255 char),
	credit int not null,
	teacher varchar2(255 char),
	primary key (id),
	unique (name)
);
	
CREATE TABLE CORPUS.taken_course (
	r_id number(19,0) not null,
	c_id number(19,0) not null,
  mark int,
	primary key(r_id, c_id),
	constraint fk_registered_user foreign key (r_id) 
    references CORPUS.registered_user(id) ON DELETE CASCADE,
	constraint fk_course foreign key (c_id) 
    references CORPUS.course(id) ON DELETE CASCADE
);

CREATE TABLE CORPUS.password_settings (
	id int default(1) not null,
	min_length int default(8) not null,
	max_length int default(16) not null,
	min_dig_char int default(1) not null,
	min_upper_char int default(1) not null,
	min_lower_char int default(1) not null,
	min_rules int default(3) not null,
	primary key(id),
	constraint chk_lock check(id = 1 or id = 2)
);

CREATE TABLE CORPUS.message (
	id number(19,0) not null,
  creator_id number(19,0) not null,
	subject varchar2(255 char) not null,
  message varchar2(4000 char) not null,
  time timestamp not null,
	primary key (id),
  constraint fk_registered_user2 foreign key (creator_id) 
    references CORPUS.registered_user(id) ON DELETE CASCADE
);

CREATE TABLE CORPUS.inbox (
	r_id number(19,0) not null,
	m_id number(19,0) not null,
  seen char(1) not null check (seen in ( 'Y', 'N' )),
	primary key(r_id, m_id),
	constraint fk_registered_user3 foreign key (r_id) 
    references CORPUS.registered_user(id) ON DELETE CASCADE,
	constraint fk_message foreign key (m_id) 
    references CORPUS.message(id) ON DELETE CASCADE
);

CREATE SEQUENCE CORPUS.registered_user_seq;
CREATE SEQUENCE CORPUS.course_seq;
CREATE SEQUENCE CORPUS.message_seq;

INSERT INTO CORPUS.password_settings(id) VALUES(1);
COMMIT;

CREATE OR REPLACE TRIGGER CORPUS.UPDATE_PASSWORD_TRIGGER
	BEFORE DELETE OR INSERT OR UPDATE ON CORPUS.PASSWORD_SETTINGS
	FOR EACH ROW
WHEN (NEW.id = 1)
BEGIN
	:NEW.id             := :OLD.id;
	:NEW.min_length     := :OLD.min_length;
	:NEW.max_length     := :OLD.max_length;
	:NEW.min_dig_char   := :OLD.min_dig_char;
	:NEW.min_upper_char := :OLD.min_upper_char;
	:NEW.min_lower_char := :OLD.min_lower_char;
	:NEW.min_rules      := :OLD.min_rules;
	  
	RAISE_APPLICATION_ERROR(-20999, 'First row cannot be deleted or modified.');
END;
