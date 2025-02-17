------------------------------------------------------------
------------------- MEMBERDao에 들어갈 query -----------------
------------------------------------------------------------
-- (1) 회원id 중복체크
SELECT * FROM MVC_MEMBER WHERE MID='aaa';

-- (2) 회원 EMAIL 중복체크
SELECT COUNT(*) CNT FROM MVC_MEMBER WHERE MEMAIL='hong@naver.com';

-- (3) 회원가입
INSERT INTO MVC_MEMBER (MID, MPW, MNAME, MEMAIL, MPHOTO, MBIRTH, MADDRESS)
    VALUES ('bbb','1','강길동','kang@naver.com','NOIMG.JPG','1994/08/25','강서구');
COMMIT;

-- (4) 로그인
SELECT * FROM MVC_MEMBER WHERE mID='aaa' and mPW='1';

-- (5) mid로 dto 가져오기(로그인 성공시 session에 넣기 위함)
SELECT * FROM MVC_MEMBER WHERE MID='aaa';

-- (6) 회원정보 수정
UPDATE MVC_MEMBER 
    SET MPW='1',
        MNAME = '송중기',
        MEMAIL = 'SONG@S.COM',
        MPHOTO = 'NOIMG.JPG',
        MBIRTH = '1995-12-12',
        MADDRESS = '서대문'
    WHERE MID = 'aaa';
SELECT * FROM MVC_MEMBER;
COMMIT;

-- (7) 회원 리스트 보기 (top-n)
SELECT * FROM MVC_MEMBER ORDER BY MRDATE DESC; -- 신입회원순

SELECT * 
    FROM (SELECT ROWNUM RN, A. * FROM (SELECT * FROM MVC_MEMBER ORDER BY MRDATE DESC) A)
    WHERE RN BETWEEN 4 AND 6;

-- (8) 전체 등록된 회원수
SELECT COUNT(*) CNT FROM MVC_MEMBER;

-- (9) 회원탈퇴
DELETE FROM MVC_MEMBER WHERE MID = 'jo';
COMMIT;
SELECT * FROM MVC_MEMBER;

------------------------------------------------------------
------------------- BoardDao 들어갈 query -------------------
------------------------------------------------------------

-- (1) 글목록 (startRow ~ endRow)    
SELECT MNAME, F.*
    FROM FILEBOARD F, MVC_MEMBER M
    WHERE F.MID = M.MID
    ORDER BY fGROUP DESC, fSTEP;

SELECT * 
    FROM (SELECT ROWNUM RN, A.*
        FROM (SELECT MNAME, F.*
               FROM FILEBOARD F, MVC_MEMBER M
        WHERE F.MID = M.MID
        ORDER BY fGROUP DESC, fSTEP) A)
    WHERE RN BETWEEN 1 AND 3;
    
-- (2) 글갯수
SELECT COUNT(*) FROM FILEBOARD;

-- (3) 글쓰기 (원글쓰기)
INSERT INTO FILEBOARD (FID, MID, FTITLE, FCONTENT, FFILENAME, FGROUP, FSTEP, FINDENT, FIP)
    VALUES (FILEBOARD_SEQ.NEXTVAL, 'aaa', '1번 글', '본문1', 'NOIMG.JPG', FILEBOARD_SEQ.CURRVAL, 0, 0, '192.1.1.1');
    
-- (4) hit (조회수) 1회 올리기
UPDATE FILEBOARD SET fHIT = fHIT + 1 WHERE FID = 1;
SELECT * FROM FILEBOARD;

-- (5) 글번호 (fid)로 글 전체 내용(BoardDto) 가져오기
SELECT F.*, MNAME
  FROM FILEBOARD F, MVC_MEMBER M WHERE F.MID=M.MID AND FID=1;

-- (6) 글 수정하기(fid, ftitle, fcontent, ffilename, frdate(SYSDATE), fip 수정)
UPDATE FILEBOARD SET 
                    FTITLE = '바뀐제목',
                    FCONTENT = '바뀐본문',
                    fFILENAME = NULL,
                    FIP = '192.168.151.10',
                    FRDATE = SYSDATE
            WHERE FID = 1;
    
-- (7) 글 삭제하기 (fid로)
DELETE FROM FILEBOARD WHERE FID = 4;

-- (8) 답변글 쓰기 전 단계 (원글의 fgroup과 같고, 원글의 fstep 보다 크면 fstep을 하나 증가)
UPDATE FILEBOARD 
    SET fSTEP = fSTEP + 1
    WHERE FGROUP = 2 AND FSTEP >0;

-- (9) 답변글 쓰기
SELECT * FROM FILEBOARD WHERE FID =2;
INSERT INTO FILEBOARD (FID, MID, FTITLE, FCONTENT, FFILENAME, FGROUP, FSTEP, FINDENT, FIP)
    VALUES (FILEBOARD_SEQ.NEXTVAL, 'bbb', '답변글', '답본문', 'NOIMG.JPG', 2, 1, 1, '192.1.1.1');

-- (10) 회원탈퇴시 탈퇴하는 회원 (mid)이 쓴 글 모두 삭제하기
DELETE FROM FILEBOARD WHERE mID = 'ccc';

SELECT * FROM FILEBOARD;
COMMIT;
------------------------------------------------------------
------------------- AdminDao에 들어갈 query ------------------
------------------------------------------------------------

-- (1) admin 로그인
SELECT * FROM ADMIN WHERE aID='admin' and aPW='1';

-- (2) 로그인 후 세션에 넣을 용도
SELECT * FROM ADMIN WHERE aID='admin';

COMMIT;











