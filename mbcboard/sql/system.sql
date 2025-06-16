create user boardtest identified by boardtest;
grant resource, connect to boardtest;
grant create session to boardtest; 
alter user boardtest default tablespace users;
alter user boardtest temporary tablespace temp;


create profile profile_test LIMIT 
 FAILED_LOGIN_ATTEMPTS 5
 PASSWORD_LOCK_TIME 1;

 SELECT id, profile
 FROM member
 WHERE id='leelee';
 
DROP profile profile_test CASCADE;


