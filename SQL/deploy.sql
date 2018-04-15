set serveroutput on format wrapped;

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_ChildDiet';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_ChildDiet');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Diet';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Diet');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_family';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_family');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Users';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Users');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE ACVL_Children_id_seq';
    DBMS_OUTPUT.put_line('DROP SEQUENCE ACVL_Children_id_seq');
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Children';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Children');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/


CREATE TABLE ACVL_Users (
    username VARCHAR2(32) NOT NULL PRIMARY KEY,
    passwd RAW(32) NOT NULL
);

CREATE SEQUENCE ACVL_Children_id_seq;
CREATE TABLE ACVL_Children (
    id number(6) DEFAULT ACVL_Children_id_seq.nextval PRIMARY KEY,
    firstname VARCHAR2(32) NOT NULL,
    lastname VARCHAR2(64) NOT NULL,
    gender CHAR(1) NOT NULL,
    grade CHAR(3) NOT NULL,
    birthdate DATE NOT NULL
);

CREATE TABLE ACVL_Family (
    username VARCHAR2(32),
    idChild number(6),
    PRIMARY KEY (username, idChild),
    FOREIGN KEY (username) references ACVL_Users(username),
    FOREIGN KEY (idChild) references ACVL_Children(id)
);

CREATE TABLE ACVL_Diet (
    diet varchar(255) PRIMARY KEY
);

CREATE TABLE ACVL_ChildDiet (
    diet varchar(255),
    idChild number(6),
    PRIMARY KEY (diet, idChild),
    FOREIGN KEY (diet) references ACVL_Diet(diet),
    FOREIGN KEY (idChild) references ACVL_Children(id)
);

select * from ACVL_CHILDREN;
select * from ACVL_Family;
SELECT * FROM ACVL_Users u, ACVL_Children c, ACVL_family f where u.username = f.username and f.idChild = c.id and u.username = 'maxime';
insert into ACVL_DIET values ('végétarien');
insert into ACVL_DIET values ('sans gluten');

select * from ACVL_ChildDiet;

SELECT * FROM ACVL_Diet;

--SELECT ACVL_Children_id_seq.next FROM dual;