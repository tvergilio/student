SET GLOBAL general_log_file = '/var/log/mysql/mariadb.log';
SET GLOBAL general_log = 1;

drop schema if exists student;
create schema student;
use student;

create table course
(
    id          bigint auto_increment primary key,
    description longtext     null,
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
VALUES (4, 'Gain an in-depth understanding of the ‘Internet of Things’ paradigm and how smart devices can be designed and deployed for a connected world.',650,
        'Smart Systems');
INSERT INTO student.course (id, description, fee, title)
VALUES (5, 'Develop an awareness of the methods and skills required to carry out Masters level research successfully. You’ll reflect critically on your own development in the context of your chosen programme of study.',550,
        'Research Practice');
INSERT INTO student.course (id, description, fee, title)
VALUES (6, 'Develop your knowledge so you can initiate, plan, execute, manage and sign off a project. Emphasis will be placed on appropriate methodologies, standards, legislation and the nine core knowledge areas associated with project management. You’ll be challenged during your learning and assessments to relate to your own experiences and/or organisations.', 700,
        'Project Management');
INSERT INTO student.course (id, description, fee, title)
VALUES (7, 'Learn about the fundamental principles and approaches for Intelligent Systems, autonomous behaviour, sensing and control, through the practical example of a simple robotic device (Delta Robot). You’ll have opportunity to work practically with the robot and develop software for simple behavioural and reaction patterns of robotic devices.', 725,
        'Intelligent Systems and Robotics');
INSERT INTO student.course (id, description, fee, title)
VALUES (8, 'This module provides the opportunity to engage in research or advanced scholarship in a subject area that is appropriate to your award and is of particular interest to you. You’ll carry out an in-depth research project, which will be discussed in a formal dissertation and viva.', 1000,
        'Dissertation');
INSERT INTO student.course (id, description, fee, title)
VALUES (9, 'Examine how to build cloud services and the technologies required to provide these to client-side systems. Well established protocols that are used to communicate with server-side software will also be examined, as will consideration for aspects such as security and n-tier systems.', 650,
        'Cloud Computing Development');
INSERT INTO student.course (id, description, fee, title)
VALUES (10, 'This module covers the principles of monitoring network performance and gathering network management data. You’ll learn to extract network parameters using industry standard tools like OpenNMS and PRTG. You’ll also be trained on SNMP protocol and will be able to put this into context of management of corporate networks.', 600,
        'Network Management');
INSERT INTO student.course (id, description, fee, title)
VALUES (11, 'This module provides an in-depth look at the Service-Oriented Architecture paradigm and, more specifically, at its recent development: Microservices. You will gain theoretical knowledge of software design using a modular, loosely coupled approach, as well as practical experience with implementation tools and techniques highly valued in the industry.', 800,
        'Software Engineering for Service Computing');
INSERT INTO student.course (id, description, fee, title)
VALUES (12, 'Gain an in-depth, systematic and critical understanding of the current research on data intelligence and issues concerning data analysis and knowledge discovery. You’ll also look at techniques for data analysis from both a theoretical and practical perspective.', 670,
        'Applied Data Analytics');
INSERT INTO student.course (id, description, fee, title)
VALUES (13, 'Understand the techniques involved in systems programming - you’ll study various approaches of design and programming modern day computer systems at a very intricate level.', 450,
        'Software and Systems');
INSERT INTO student.course (id, description, fee, title)
VALUES (14, 'This module will give you the opportunity to broaden and deepen your knowledge in your chosen areas of study. Working with your module tutor and project supervisor, you’ll research and apply current theory and practice to develop high level skills within a framework of self-directed learning.', 580,
        'Negotiated Skills Development');
INSERT INTO student.course (id, description, fee, title)
VALUES (15, 'This module provides an introduction to reverse-engineering malware binaries for the x86 architecture. You will be introduced to low level programming languages such as C and assembly language and will develop practical and theoretical skills to enable you to perform both static and dynamic analysis of malware code. This module also takes an in-depth look at typical malware behaviour and how to leverage state-of-the-art reverse-engineering tools to facilitate your analysis.', 850,
        'Reverse Engineering and Malware Analysis');
INSERT INTO student.course (id, description, fee, title)
VALUES (16, 'This module will introduce modern image/video processing techniques and applications in digital forensic investigation. You’ll learn concepts of digital image/video processing application and how to apply techniques such as image filtering, de-nosing, enhancement and restoration methods in different scenarios.', 720,
        'Forensic Image and Video Processing');
INSERT INTO student.course (id, description, fee, title)
VALUES (17, 'Deepen your understanding and experience of the technical underpinnings of software security. You’ll gain experience with software vulnerabilities and will review code with software design and implementation bugs (including buffer overflows, injection attacks, and other faults). You’ll audit code for the presence of security vulnerabilities both manually and using advanced approaches such as fuzz-testing and static analysis. You’ll apply mitigation techniques to remove vulnerabilities from software.', 550,
        'Software Security and Exploitation');
INSERT INTO student.course (id, description, fee, title)
VALUES (18, 'This is an introduction to the theories and methods that are core to historical research. You will study research skills and methods, exploring libraries, sources, archives and treatments of history using case studies. You will analyse the relationships between literary texts, historical documents, and films, as well as scrutinising how events have been recorded, historicised, fictionalised and dramatised.', 550,
        'Researching Cultures');
INSERT INTO student.course (id, description, fee, title)
VALUES (19, 'Examine the social, cultural and political history of Britain in the ''long 1960s'' (c. 1956-1974) – the period which saw a ''cultural revolution''. You''ll study various forms of cultural production in the context of social and political changes in Britain.', 350,
        'Britain in the Sixties: A Cultural Revolution');
INSERT INTO student.course (id, description, fee, title)
VALUES (20, 'Combine the study of social, cultural and environmental history to explore the changing relationship between people and their environments. You will focus on the United States, Europe and European settler societies over the last two centuries.', 450,
        'Nature, Culture and Society Explorations');
INSERT INTO student.course (id, description, fee, title)
VALUES (21, 'You will use pastiches, rewritings and parodies of the 19th-Century novel to consider how we are ''other Victorians'' and the role of the ''other'' in Victorian society.', 1150,
        'Other Victorians: The Neo-Victorian Contemporary Novel');
INSERT INTO student.course (id, description, fee, title)
VALUES (22, 'According to some theorists, a preoccupation with sexuality is one of the defining features of Western modernity. You will explore current debates, relevant theoretical approaches and will be introduced to a range of source material including newspaper reports, film and popular literature.', 470,
        'Sexuality, Gender and Popular Culture in Britain 1918-1970');
INSERT INTO student.course (id, description, fee, title)
VALUES (23, 'Study the history and historiography of modern technology in societies and cultures under colonial rule. You''ll explore the role of technology in imperial rule, attitudes and practices concerning technology and the changes that ensued. You''ll gain an understanding of how modern mechanical technologies became everyday goods. You''ll also look at how people viewed, used and abused these technologies to understand their role in wider social and cultural change.   ', 580,
        'Technology, Empire and Everyday Life after 1850');
INSERT INTO student.course (id, description, fee, title)
VALUES (24, 'This module will develop your knowledge of anatomy, physiology and function in relation to normal movement and sports. You will gain knowledge in completing joint assessment and examination, along with restoring normal joint function.', 1300,
        'Anatomical Assessment and Examination');
INSERT INTO student.course (id, description, fee, title)
VALUES (25, 'This module teaches you the life support principles to the level of the recognised first aid at work programme in the context of sports trauma management of individual and team athletes.', 1150,
        'Sports Trauma Management');
INSERT INTO student.course (id, description, fee, title)
VALUES (26, 'You will incorporate a theoretical and practical approach which develops your comprehensive understanding of injury management. You will explore key underpinning knowledge, skills and evidence for rehabilitation strategies for athletic populations, and critically review treatment strategies for conservative and surgical options inclusive of specific controlled conditions.', 950,
        'Sports Injury Management and Rehabilitation');
INSERT INTO student.course (id, description, fee, title)
VALUES (27, 'Develop specialist creative and technical skills for the making of documentary films. You will be introduced to the Northern Film School and the overall ethos of the MA while building basic production skills in three specialist areas: producing and directing; camera and sound; editing.', 550,
        'Documentary Practice 1');
INSERT INTO student.course (id, description, fee, title)
VALUES (28, 'Develop your knowledge and understanding of the principles and theoretical foundations which govern the modern marketing environment, allowing you to progress in a specialised area of marketing such as international or services marketing.', 450,
        'Contemporary Marketing');
INSERT INTO student.course (id, description, fee, title)
VALUES (29, 'Develop a strategic organisational perspective as well as a basis for progression and application of strategic level skills, competencies, and decision-making capability.', 550,
        'Corporate Strategy');
INSERT INTO student.course (id, description, fee, title)
VALUES (30, 'Gain a critical understanding of contemporary accounting and financing principles which support business decision-making and financial resourcing in both the private and public sectors.', 720,
        'Managing Financial Resources');
INSERT INTO student.course (id, description, fee, title)
VALUES (31, 'Develop functional knowledge and a critical understanding of key perspectives on human behaviour within an organisation and on the nature and processes of organising and managing human activity.', 950,
        'Management, People and Organisations');
INSERT INTO student.course (id, description, fee, title)
VALUES (32, 'This module explores a range of entrepreneurship theories and practices and involves an in-depth critical examination of business start-ups, the experiences of entrepreneurs and key aspects of small business management.', 580,
        'Entrepreneurship');
INSERT INTO student.course (id, description, fee, title)
VALUES (33, 'You will focus on the 1990s and 2000s - including the US-led globalisation project, the spread of global markets, the dotcom crash, 9/11 attacks on America and the bursting of the housing bubble.', 350,
        'Neoliberal Fictions');
INSERT INTO student.course (id, description, fee, title)
VALUES (34, 'This module will introduce a range of post-millennial Anglophone Caribbean texts. You''ll discuss topics such as how the form and themes of the texts are shaped around music, and whether contemporary Caribbean writers are still responding to postcolonial concerns or if they attempt to untether themselves from the label of the ''postcolonial''. The module will develop your capacity to share ideas and articulate arguments verbally, in particular through oral presentations and group work, and in creative and academic writing.', 710,
        'Contemporary Caribbean Writing');

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