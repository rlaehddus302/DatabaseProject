INSERT INTO academic_term (academic_year, semester)
SELECT * FROM CSVREAD('classpath:AcademicTerm.csv', null, 'charset=UTF-8');

INSERT INTO COURSE (credit, area, curriculum, name, code, academic_Term_ID)
SELECT * FROM CSVREAD('classpath:Course.csv', null, 'charset=UTF-8');

INSERT INTO SESSION (courseID, PROFESSOR, REMARKS, sessionCODE)
SELECT * FROM CSVREAD('classpath:Session.csv', null, 'charset=UTF-8');

INSERT INTO CLASS_TIME_AND_LOCATION (END_TIME, START_TIME, sessionID, LOCATION, WEEK)
SELECT * FROM CSVREAD('classpath:TNL.csv', null, 'charset=UTF-8');

INSERT INTO CUSTOMER (id, student_Number, password, name, role)
VALUES ('admin', '0000000000', '1234', 'admin', 'ROLE_ADMIN')