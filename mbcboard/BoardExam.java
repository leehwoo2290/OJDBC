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
		// mbc 게시판용 jdbc테스트
		// dto : 객체를 담당한다
		// dao : 데이터베이스에서 연동을 담당한다
		// service : 부메뉴와 서비스를 담당한다
		
		boolean brun = true;

		while (brun) {
			System.out.println("MBC 자유 게시판입니다.");
			System.out.println("1. 회원");
			System.out.println("2. 게시판");
			System.out.println("3. 종료");
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
				System.out.println("잘못된 입력 값");
				break;
			}
		}
	}

}
