package mbcboard;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dto.MemberDTO;
import mbcboard.service.BoardService;
import mbcboard.service.MemberService;

public class BoardExam {
	
	public static Scanner inputStr = new Scanner(System.in);
	public static MemberDTO session;

	public static void main(String[] args) throws SQLException {
		// mbc �Խ��ǿ� jdbc�׽�Ʈ
		// dto : ��ü�� ����Ѵ�
		// dao : �����ͺ��̽����� ������ ����Ѵ�
		// service : �θ޴��� ���񽺸� ����Ѵ�
		
		boolean brun = true;

		while (brun) {
			System.out.println("MBC ���� �Խ����Դϴ�.");
			System.out.println("1. ȸ��");
			System.out.println("2. �Խ���");
			System.out.println("3. ����");
			System.out.println(">>>");
			String select = inputStr.next();
			
			switch(select) {
			
			case "1":
				MemberService memberService = new MemberService();
				session = memberService.subMenu(inputStr, session);			
				break;
			case "2":
				BoardService boardService = new BoardService();
				boardService.subMenu(inputStr, session);
				break;
			case "3":
				brun = false;
				break;
			default:
				System.out.println("�߸��� �Է� ��");
				break;
			}
		}
	}

}
