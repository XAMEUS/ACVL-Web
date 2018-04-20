set serveroutput on format wrapped;

COMMIT;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Wishes';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Wishes');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_ActivityPeriods';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_ActivityPeriods');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Registrations';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Registrations');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
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
    EXECUTE IMMEDIATE 'DROP SEQUENCE ACVL_Activities_id_seq';
    DBMS_OUTPUT.put_line('DROP SEQUENCE ACVL_Activities_id_seq');
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Activities';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Activities');
EXCEPTION
WHEN OTHERS THEN
   IF SQLCODE != -942 THEN
      RAISE;
   END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE ACVL_Periods_id_seq';
    DBMS_OUTPUT.put_line('DROP SEQUENCE ACVL_Periods_id_seq');
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ACVL_Periods';
    DBMS_OUTPUT.put_line('DROP TABLE ACVL_Periods');
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

CREATE SEQUENCE ACVL_Periods_id_seq;
CREATE TABLE ACVL_Periods (
    idPeriod number(3) DEFAULT ACVL_Periods_id_seq.nextval PRIMARY KEY,
    limitDate DATE,
    startDate DATE,
    endDate DATE
);

CREATE SEQUENCE ACVL_Activities_id_seq;
CREATE TABLE ACVL_Activities (
    id number(6) DEFAULT ACVL_Activities_id_seq.nextval PRIMARY KEY,
    capacity int,
    codeGrades int, -- 1=PS, 2=MS, 4=GS, etc. 5=PS+GS
    codeDays int, -- idem
    codeStrategy int,
    title VARCHAR2(100),
    description VARCHAR2(500),
    animators VARCHAR2(500)
);

CREATE TABLE ACVL_ActivityPeriods (
    activity number(6),
    period number(3),
    PRIMARY KEY (activity, period),
    FOREIGN KEY (activity) references ACVL_Activities(id),
    FOREIGN KEY (period) references ACVL_Periods(idPeriod)
);

CREATE TABLE ACVL_Registrations (
    child number(6),
    period number(3),
    codeCantine int, -- 1, 2, 3=2+1 etc.
    codeGarderie int, -- idem
    infos VARCHAR2(500),
    PRIMARY KEY (child, period),
    FOREIGN KEY (child) references ACVL_Children(id),
    FOREIGN KEY (period) references ACVL_Periods(idPeriod)
);

CREATE TABLE ACVL_Wishes (
    child number(6),
    period number(3),
    activity number(6),
    rank int,
    day number(1), -- 1, 2, 3, 4, 5
    PRIMARY KEY (child, period, activity, day),
    FOREIGN KEY (child) references ACVL_Children(id),
    FOREIGN KEY (activity) references ACVL_Activities(id),
    FOREIGN KEY (period) references ACVL_Periods(idPeriod)
);

CREATE TABLE ACVL_ActivitiesRegistrations (
    child number(6),
    period number(3),
    activity number(6),
    day number(1), -- 1, 2, 3, 4, 5
    PRIMARY KEY (child, period, activity, day),
    FOREIGN KEY (child) references ACVL_Children(id),
    FOREIGN KEY (activity) references ACVL_Activities(id),
    FOREIGN KEY (period) references ACVL_Periods(idPeriod)
);


select * from ACVL_CHILDREN;
select * from ACVL_Family;
SELECT * FROM ACVL_Users u, ACVL_Children c, ACVL_family f where u.username = f.username and f.idChild = c.id and u.username = 'maxime';
insert into ACVL_DIET values ('végétarien');
insert into ACVL_DIET values ('sans gluten');
COMMIT;
select * from ACVL_ChildDiet;
SELECT * FROM ACVL_Periods where idPeriod NOT IN (select period from ACVL_Registrations r WHERE r.child = 1);
select * from ACVL_Periods;
Select * from ACVL_Activities;
SELECT * FROM ACVL_Diet;
Select * from ACVL_Wishes;

--SELECT ACVL_Children_id_seq.next FROM dual;