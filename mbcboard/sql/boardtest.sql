create table member(
mno number(5) not null,
bwriter nvarchar2(10) not null, 
id nvarchar2(10) primary key check(length(id)>=6),	--boardtable에 bwriter와 fk로 관계 설정함
pw nvarchar2(16) not null check(length(pw)>=8),
regidate date default sysdate not null, -- 입력 안해도 디폴트값으로 값들어감 
activate char(1) default 'Y' not null, --회원 탈퇴시 false (bool이없음;)
CONSTRAINT pw_check_lower 
CHECK(REGEXP_LIKE(pw,'[[:lower:]]') 
AND REGEXP_LIKE(pw, '[[:upper:]]')
AND REGEXP_LIKE(pw, '[[:digit:]]')
AND REGEXP_LIKE(pw, '[[:punct:]]'))
)

drop table member

create sequence member_seq increment by 1 start with 1 nocycle nocache
drop sequence member_seq

update member set bwriter='더미이름', pw='12345' where id='park'

insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '이현우', 'leelee', 'LEE03lee@@');
insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '홍경훈', 'hong', 'Honghong@@');
insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '박채은', 'park', 'park0321@@');
insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '양지민', 'yang', 'Yy1@');


select* from member

delete from member

select id from member where id = 'kkw' and pw = '123'

select id from member where id = 'admin'

drop table member
--이미 만들어둔 시퀀스 객체 사용 (board_seq)
--------------------------------
delete from board
drop table board

drop sequence board_seq

create table board(
	bno number(5) primary key, 
	btitle nvarchar2(30) not null, 
	bcontent nvarchar2(1000) not null,        
	bwriter nvarchar2(10) not null,
	bdate date default sysdate not null,  
	activate char(1) default 'Y' not null --삭제시 false (bool이없음;)
) --board 테이블 생성

alter table board add constraint board_member_fk foreign key (bwriter) references member(id)
--board테이블은 member의 자식 테이블, mname과 id 관계설정
--board에 더미데이터 넣으면 부모 테이블에 id값이 없어서 값이 안들어감



create sequence board_seq increment by 1 start with 1 nocycle nocache

insert into board(bno, btitle, bcontent, bwriter, bid, bdate)
values(board_seq.nextval, '타이틀1', '내용1', '이현우' ,'kkw', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '이름2', '부서2', 'lee', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '이름3', '부서3', 'park', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '이름4', '부서4', 'yang', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '이름5', '부서5', 'kkw', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '이름6', '부서6', 'kkw', sysdate);

select* from board;

--select replace(bwriter,substr(bwriter,0, length(bwriter) - 1), '**' ) from board

select b.bno, b.btitle, b.bwriter, b.bdate from member m inner join board b on m.id = b.bwriter where m.id='lee'

--------------------------------
--조인 : 2개 테이블 연결해서 값 가져온다

select b.*, m.bwriter from member m inner join board b on m.id = b.bwriter where id='kkw'
---------------------------------
create table boardcomment(
	cno number(5) not null,  
	ccontent nvarchar2(1000) not null,        
	cwriter nvarchar2(10) not null,
	cdate date default sysdate not null,
	activate char(1) default 'Y' not null --삭제시 false (bool이없음;)
) --comment 테이블 생성

drop table boardcomment

alter table boardcomment add constraint comment_board_fk foreign key (cno) references board(bno)

alter table boardcomment add constraint comment_member_fk foreign key (cwriter) references member(id)

select b.bno, b.btitle, b.bcontent, b.bwriter, b.bdate, c.ccontent, c.cwriter, c.cdate 
from board b, boardcomment c where b.bno= 1 and b.activate= 'Y' and c.activate= 'Y'
