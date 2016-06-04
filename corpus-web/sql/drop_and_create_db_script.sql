DROP TABLE CORPUS.registereduser cascade constraints;
DROP TABLE CORPUS.registereduser_course cascade constraints;
DROP TABLE CORPUS.course cascade constraints;
DROP TABLE CORPUS.password_settings cascade constraints;

DROP SEQUENCE CORPUS.registereduser_seq;
DROP SEQUENCE CORPUS.course_seq;

CREATE TABLE CORPUS.registereduser (
	id number(19,0) not null,
	username varchar2(255 char) not null,
	password varchar2(255 char) not null,
	fullname varchar2(255 char),
	email varchar2(255 char),
	role int not null,
	primary key (id),
	unique (username, email)
);
	
CREATE TABLE CORPUS.course (
	id number(19,0) not null,
	name varchar2(255 char) not null,
	room int,
	credit int not null,
	professor varchar2(255 char),
	primary key (id),
	unique (name)
);
	
CREATE TABLE CORPUS.registereduser_course (
	r_id number(19,0) not null,
	c_id number(19,0) not null,
	primary key(r_id, c_id),
	constraint fk_course foreign key (r_id) references CORPUS.registereduser(id),
	constraint fk_user foreign key (c_id) references CORPUS.course(id)
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

CREATE SEQUENCE CORPUS.registereduser_seq;
CREATE SEQUENCE CORPUS.course_seq;

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
