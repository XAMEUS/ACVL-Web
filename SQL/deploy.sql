set serveroutput on format wrapped;

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

CREATE TABLE ACVL_Users (
    username VARCHAR2(32) NOT NULL PRIMARY KEY,
    passwd RAW(32) NOT NULL
);
