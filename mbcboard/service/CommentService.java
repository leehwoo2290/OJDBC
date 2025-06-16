package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.CommentDAO;
import mbcboard.dto.MemberDTO;

public class CommentService {

	public CommentDAO commentDAO = new CommentDAO(); // 1,2단계실행
	
	public void subMenu(int boardNum, Scanner inputStr, MemberDTO session) throws SQLException {
		
		boolean bSubRun = true;
		
		while(bSubRun) {
			
			System.out.println("댓글 서비스 시작");
			System.out.println("1. 댓글 작성");
			System.out.println("2. 댓글 삭제");
			System.out.println("3. 나가기");

			System.out.println(">>>");
			
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			
			case "1":
				System.out.println("댓글 작성");
				insert(boardNum, session);
				break;
			case "2":
				System.out.println("댓글 삭제");
				delete(boardNum, session);
				break;
			case "3":
				System.out.println("댓글 서비스 종료");
				commentDAO.connection.close();
				bSubRun = false;
				break;
			default:
				System.out.println("잘못된 입력 값");
				break;
			}
		}
		
	}

	private void delete(int boardNum, MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		
		System.out.println("==========================");
		System.out.println("댓글 삭제\n");
		
		commentDAO.delete(boardNum, session);
	}

	private void insert(int boardNum, MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		
		System.out.println("==========================");
		System.out.println("댓글 추가\n");
		
		Scanner inputNextLine = new Scanner(System.in);
		
		String commentContent = inputNextLine.nextLine();
		
		commentDAO.insert(boardNum, session, commentContent);
	}
}
