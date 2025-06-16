create table member(
mno number(5) not null,
bwriter nvarchar2(10) not null, 
id nvarchar2(10) primary key check(length(id)>=6),	--boardtable�� bwriter�� fk�� ���� ������
pw nvarchar2(16) not null check(length(pw)>=8),
regidate date default sysdate not null, -- �Է� ���ص� ����Ʈ������ ���� 
activate char(1) default 'Y' not null, --ȸ�� Ż��� false (bool�̾���;)
CONSTRAINT pw_check_lower 
CHECK(REGEXP_LIKE(pw,'[[:lower:]]') 
AND REGEXP_LIKE(pw, '[[:upper:]]')
AND REGEXP_LIKE(pw, '[[:digit:]]')
AND REGEXP_LIKE(pw, '[[:punct:]]'))
)

drop table member

create sequence member_seq increment by 1 start with 1 nocycle nocache
drop sequence member_seq

update member set bwriter='�����̸�', pw='12345' where id='park'

insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '������', 'leelee', 'LEE03lee@@');
insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, 'ȫ����', 'hong', 'Honghong@@');
insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '��ä��', 'park', 'park0321@@');
insert into member(mno, bwriter, id, pw)
values(member_seq.nextval, '������', 'yang', 'Yy1@');


select* from member

delete from member

select id from member where id = 'kkw' and pw = '123'

select id from member where id = 'admin'

drop table member
--�̹� ������ ������ ��ü ��� (board_seq)
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
	activate char(1) default 'Y' not null --������ false (bool�̾���;)
) --board ���̺� ����

alter table board add constraint board_member_fk foreign key (bwriter) references member(id)
--board���̺��� member�� �ڽ� ���̺�, mname�� id ���輳��
--board�� ���̵����� ������ �θ� ���̺� id���� ��� ���� �ȵ�



create sequence board_seq increment by 1 start with 1 nocycle nocache

insert into board(bno, btitle, bcontent, bwriter, bid, bdate)
values(board_seq.nextval, 'Ÿ��Ʋ1', '����1', '������' ,'kkw', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '�̸�2', '�μ�2', 'lee', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '�̸�3', '�μ�3', 'park', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '�̸�4', '�μ�4', 'yang', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '�̸�5', '�μ�5', 'kkw', sysdate);
insert into board(bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '�̸�6', '�μ�6', 'kkw', sysdate);

select* from board;

--select replace(bwriter,substr(bwriter,0, length(bwriter) - 1), '**' ) from board

select b.bno, b.btitle, b.bwriter, b.bdate from member m inner join board b on m.id = b.bwriter where m.id='lee'

--------------------------------
--���� : 2�� ���̺� �����ؼ� �� �����´�

select b.*, m.bwriter from member m inner join board b on m.id = b.bwriter where id='kkw'
---------------------------------
create table boardcomment(
	cno number(5) not null,  
	ccontent nvarchar2(1000) not null,        
	cwriter nvarchar2(10) not null,
	cdate date default sysdate not null,
	activate char(1) default 'Y' not null --������ false (bool�̾���;)
) --comment ���̺� ����

drop table boardcomment

alter table boardcomment add constraint comment_board_fk foreign key (cno) references board(bno)

alter table boardcomment add constraint comment_member_fk foreign key (cwriter) references member(id)

select b.bno, b.btitle, b.bcontent, b.bwriter, b.bdate, c.ccontent, c.cwriter, c.cdate 
from board b, boardcomment c where b.bno= 1 and b.activate= 'Y' and c.activate= 'Y'
