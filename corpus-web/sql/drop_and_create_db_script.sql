  drop table JAVA_A_GYAKORLATBAN.registereduser cascade constraints;
  drop table JAVA_A_GYAKORLATBAN.registereduser_course cascade constraints;
  drop table JAVA_A_GYAKORLATBAN.course cascade constraints;
  
  drop sequence JAVA_A_GYAKORLATBAN.registereduser_seq;
  drop sequence JAVA_A_GYAKORLATBAN.course_seq;

  create table JAVA_A_GYAKORLATBAN.registereduser (
    id number(19,0) not null,
    username varchar2(255 char) not null,
    password varchar2(255 char) not null,
    fullname varchar2(255 char),
    email varchar2(255 char),
    role int not null,
    primary key (id),
    unique (id, username, email)
  );
	
	create table JAVA_A_GYAKORLATBAN.course (
    id number(19,0) not null,
    name varchar2(255 char) not null,
    room int,
		credit int not null,
    professor varchar2(255 char),
    primary key (id),
    unique (id, name)
  );
	
	create table JAVA_A_GYAKORLATBAN.registereduser_course (
    r_id number(19,0) not null,
    c_id number(19,0) not null,
    primary key(r_id, c_id),
    constraint fk_course foreign key (r_id) references JAVA_A_GYAKORLATBAN.registereduser(id),
    constraint fk_user foreign key (c_id) references JAVA_A_GYAKORLATBAN.course(id)
  );

  create sequence JAVA_A_GYAKORLATBAN.registereduser_seq;
  create sequence JAVA_A_GYAKORLATBAN.course_seq;