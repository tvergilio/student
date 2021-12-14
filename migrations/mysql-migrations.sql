SET GLOBAL general_log_file = '/var/log/mysql/mariadb.log';
SET GLOBAL general_log = 1;

drop schema if exists student;
create schema student;
use student;

create table course
(
    id          bigint auto_increment primary key,
    description varchar(255) null,
    fee         double       null,
    title       varchar(255) null
);

create table portal_user
(
    id        bigint auto_increment primary key,
    email     varchar(45) not null,
    password  varchar(64) not null,
    role      int         null,
    user_name varchar(20) not null,
    constraint UK_9eg0sqxgl1lcy1pmnu6mta5vw unique (user_name),
    constraint UK_npmhp9oynwc4rdjxg20jl0nt3 unique (email)
);

create table student
(
    id         bigint auto_increment primary key,
    forename   varchar(255) null,
    student_id varchar(255) null,
    surname    varchar(255) null,
    constraint UK_lh7am6sc9pv0nhyg7qkj7w5d3 unique (student_id)
);

create table enrolment
(
    course_id  bigint not null,
    student_id bigint not null,
    primary key (course_id, student_id),
    constraint FKqnrv6xltxnx61nfjoe2sngny4 foreign key (course_id) references course (id),
    constraint FKquem30hnspsnegde2q2bhvou foreign key (student_id) references student (id)
);

create table portal_user_student
(
    student_id bigint null,
    user_id    bigint not null primary key,
    constraint FKdw32ami43fm7ttefy8bw0nsb4 foreign key (student_id) references student (id),
    constraint FKrp3bw0f99i3nor9f1qkcyq77t foreign key (user_id) references portal_user (id)
);

INSERT INTO student.student (id, forename, student_id, surname)
VALUES (1, 'Walter', 'c3781247', 'White');
INSERT INTO student.student (id, forename, student_id, surname)
VALUES (2, 'Jesse', 'c3922382', 'Pinkman');

INSERT INTO student.portal_user (id, email, password, role, user_name)
VALUES (1, 'w.white@gmail.com', 'password', 0, 'walterwhite');

INSERT INTO student.portal_user_student (student_id, user_id)
VALUES (1, 1);

INSERT INTO student.course (id, description, fee, title)
VALUES (1, 'An introductory course covering the fundamentals of object-oriented programming.', 150,
        'Introduction to Programming');
INSERT INTO student.course (id, description, fee, title)
VALUES (2, 'A course covering the learning objectives for the latest OCPJP certification.', 350,
        'OCPJP Exam Preparation');
INSERT INTO student.course (id, description, fee, title)
VALUES (3, 'An intermediate course on how to make the most of Spring Boot''s latest functionality.', 200,
        'Spring Boot');
INSERT INTO student.course (id, description, fee, title)
VALUES (4, 'An advanced course covering the in-lab production of medical-grade synthetic substances.', 1150,
        'Advanced Chemistry');

INSERT INTO student.enrolment (course_id, student_id)
VALUES (1, 2);
INSERT INTO student.enrolment (course_id, student_id)
VALUES (3, 1);
INSERT INTO student.enrolment (course_id, student_id)
VALUES (4, 1);

DROP USER IF EXISTS 'student-spring-user'@'%';
CREATE USER 'student-spring-user'@'%' IDENTIFIED BY 'student-secret';
GRANT ALL PRIVILEGES on student.* to `student-spring-user`;
FLUSH PRIVILEGES;