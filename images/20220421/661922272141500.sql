select * from USERS;


select * from BOARD;

select * from COMMENTS;

select * from board_comment;
UPDATE BOARD
SET
TITLE = 'test'
WHERE
BOARD_IDX = 1;

DROP TABLE BOARD_COMMENT;

DELETE FROM BOARD WHERE BOARD_IDX = 2;

SELECT * FROM all_all_tables;

CREATE TABLE COMMENTS 
( 
    IDX       NUMBER(4)	NOT NULL,
    COMM       VARCHAR2(250),
    USERNAME         VARCHAR2(20),
    BOARDIDX         NUMBER(4),
    CREATE_DATETIME    DATE
);
ALTER TABLE SYSTEM.COMMENTS DROP COLUMN BOARDIDX;
ALTER TABLE 테이블명 ADD(컬럼명 데이타타입(사이즈));

출처: https://jwklife.tistory.com/5 [인 생]
