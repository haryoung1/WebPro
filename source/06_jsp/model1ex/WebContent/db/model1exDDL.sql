-- TABLE & SEQUENCE DROP
DROP TABLE FILEBOARD;
DROP TABLE CUSTOMER;
DROP TABLE BOOK;
DROP SEQUENCE FILEBOARD_SEQ;
DROP SEQUENCE BOOK_SEQ;

-- TABLE & SEQUENCE CREATE

CREATE TABLE CUSTOMER(
    cID VARCHAR2(50) PRIMARY KEY, -- 아이디
    cPW VARCHAR2(50) NOT NULL,    -- 비밀번호
    cNAME VARCHAR2(50) NOT NULL,  -- 이름
    cTEL  VARCHAR2(50),           -- 전화번호
    cEMAIL VARCHAR2(50),          -- 이메일
    cADDRESS VARCHAR2(250),       -- 주소
    cGENDER  VARCHAR2(10),        -- 성별
    cBIRTH DATE DEFAULT SYSDATE,  -- 생년월일
    cRDATE DATE DEFAULT SYSDATE NOT NULL  -- 가입시점
);

CREATE SEQUENCE CUSTOMER_SEQ
    MAXVALUE 9999999
    NOCACHE
    NOCYCLE;
    
CREATE TABLE FILEBOARD (
    fNUM NUMBER(7,0) PRIMARY KEY,       -- 글번호
    cID VARCHAR2(50),                   -- 아이디
    fSUBJECT VARCHAR2(50) NOT NULL,     -- 글제목
    fCONTENT VARCHAR2(4000),            -- 본문
    fFILENAME VARCHAR2(50),             -- 첨부파일 이름
    fPW VARCHAR2(50) NOT NULL,          -- 삭제용 비번
    fHIT NUMBER(7) DEFAULT 0 NOT NULL,                  -- 조회수
    fREF NUMBER(7),                   -- 글그룹
    fRE_STEP NUMBER(7),               -- 그룹 내 출력순서
    fRE_LEVEL NUMBER(1),                -- 들여쓰기 정도
    fIP VARCHAR2(50),                   -- IP 주소
    fRDATE DATE DEFAULT SYSDATE,         -- 글 등록시점
    FOREIGN KEY (cID) REFERENCES CUSTOMER (cID) 
);

CREATE SEQUENCE FILEBOARD_SEQ
    MAXVALUE 9999999
    NOCACHE
    NOCYCLE;
    
CREATE TABLE BOOK(
    bID NUMBER(7,0) PRIMARY KEY,
    bTITLE  VARCHAR2(50) NOT NULL, -- 책이름
    bPRICE  NUMBER(7,0)     NOT NULL, -- 책가격
    bIMAGE1 VARCHAR2(50) NOT NULL, -- 책 대표 이미지
    bIMAGE2 VARCHAR2(50) NOT NULL, -- 책 추가 이미지
    bCONTENT VARCHAR2(4000),        -- 책 소개
    bDISCOUNT NUMBER(3)   NOT NULL, -- 할인율
    bRDATE DATE DEFAULT SYSDATE NOT NULL
);

CREATE SEQUENCE BOOK_SEQ
    MAXVALUE 9999999
    NOCACHE
    NOCYCLE;

SELECT * FROM CUSTOMER;
SELECT * FROM FILEBOARD;
SELECT * FROM BOOK;
COMMIT;
-- 인덱스나 뷰 생성