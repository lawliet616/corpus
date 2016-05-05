	drop table JAVA_A_GYAKORLATBAN.registereduser cascade constraints;
    drop table JAVA_A_GYAKORLATBAN.registereduser_course cascade constraints;
    drop table JAVA_A_GYAKORLATBAN.course cascade constraints;

    create table JAVA_A_GYAKORLATBAN.registereduser (
        id number(19,0) not null,
        username varchar2(255 char),
        password varchar2(255 char),
		fullname varchar2(255 char),
        email varchar2(255 char),
        role int,
        primary key (id)
    );
	
	create table JAVA_A_GYAKORLATBAN.course (
        id number(19,0) not null,
        name varchar2(255 char),
        room int,
		credit int,
        professor varchar2(255 char),
        primary key (id)
    );
	
	create table JAVA_A_GYAKORLATBAN.registereduser_course (
        r_id number(19,0),
		c_id number(19,0),
		constraint fk_course foreign key (r_id) references registereduser(id),
		constraint fk_course foreign key (c_id) references course(id)
    );
