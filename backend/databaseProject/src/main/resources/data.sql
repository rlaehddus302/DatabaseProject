INSERT INTO COURSE (credit, area, curriculum, name, code)
SELECT * FROM CSVREAD('classpath:Course.csv', null, 'charset=UTF-8');

INSERT INTO SESSION (courseID, PROFESSOR, REMARKS, sessionCODE)
SELECT * FROM CSVREAD('classpath:Session.csv', null, 'charset=UTF-8');

INSERT INTO CLASS_TIME_AND_LOCATION (END_TIME, START_TIME, sessionID, LOCATION, WEEK)
SELECT * FROM CSVREAD('classpath:TNL.csv', null, 'charset=UTF-8');
