INSERT INTO COURSE
SELECT * FROM CSVREAD('classpath:Course.csv', null, 'charset=UTF-8');

INSERT INTO COURSE_CODE
SELECT * FROM CSVREAD('classpath:CourseCode.csv', null, 'charset=UTF-8');

INSERT INTO SESSION
SELECT * FROM CSVREAD('classpath:Session.csv', null, 'charset=UTF-8');

INSERT INTO CLASS_TIME_AND_LOCATION
SELECT * FROM CSVREAD('classpath:TNL.csv', null, 'charset=UTF-8');
