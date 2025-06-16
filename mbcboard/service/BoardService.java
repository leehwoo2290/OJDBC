package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.BoardDAO;
import mbcboard.dao.CommentDAO;
import mbcboard.dto.BoardDTO;
import mbcboard.dto.CommentDTO;
import mbcboard.dto.MemberDTO;

public class BoardService {
	//dao dto 사용하여 부메뉴와 crud처리
	
	public BoardDAO boardDAO = new BoardDAO(); // 1,2단계실행
	
	public void subMenu(Scanner inputStr, MemberDTO session) throws SQLException {
		
		boolean bSubRun = true;
		
		while(bSubRun) {
			
			System.out.println("게시판 서비스 시작");
			System.out.println("1. 모두보기");
			System.out.println("2. 게시글 작성");
			System.out.println("3. 게시글 자세히 보기");
			System.out.println("4. 내 게시물 보기");
			System.out.println("5. 게시글 수정");
			System.out.println("6. 게시글 삭제");
			System.out.println("7. 나가기");
			System.out.println(">>>");
			
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			
			case "1":
				System.out.println("모든 게시물 보기");
				selectAll();
				break;
			case "2":
				System.out.println("게시물 작성");
				insertBoard(session);
				break;
			case "3":
				System.out.println("게시물 자세히 보기");
				readOne(inputStr, session);
				break;
			case "4":
				System.out.println("내 게시물 보기");
				readBySession(inputStr, session);
				break;
			case "5":
				System.out.println("게시물 수정");
				modify(session);
				break;
			case "6":
				System.out.println("게시물 삭제");
				deleteOne(inputStr, session);
				break;
			case "7":
				System.out.println("게시물 보기 종료");
				boardDAO.connection.close();
				bSubRun = false;
				break;
			default:
				System.out.println("잘못된 입력 값");
				break;
			}
		}	
	}

	private void readBySession(Scanner inputStr, MemberDTO session) throws SQLException {

		// 로그인한 계정이 작성한 글들중 번호로 해당 글을 찾아서 내용을 수정한다	
		if(session == null) {
			
			System.out.println("로그인 중이 아닙니다.");
			return;
		}
		
		System.out.println("내가 작성한 게시글 목록");
		System.out.println("==================");
		
		boardDAO.readBySession(session);
		
		System.out.println("=========끝========");
	}

	private void deleteOne(Scanner inputStr, MemberDTO session) throws SQLException {
		
		// 로그인한 계정이 작성한 글들중 번호로 해당 글을 찾아서 내용을 수정한다	
		if(session == null) {
					
			System.out.println("로그인 중이 아닙니다.");
			return;
		}
		// 게시물의 번호를 받아 삭제한다
		System.out.println("삭제하려는 게시글의 번호를 입력하세요");
		Scanner inputInt = new Scanner(System.in);
		System.out.print(">>>");
		int selectBno = inputInt.nextInt();
		boardDAO.deleteOne(selectBno, session);
		
	}

	private void modify(MemberDTO session) throws SQLException {
		
		// 로그인한 계정이 작성한 글들중 번호로 해당 글을 찾아서 내용을 수정한다	
		if(session == null) {
			
			System.out.println("로그인 중이 아닙니다.");
			return;
		}

		boardDAO.readBySession(session);
		
		System.out.println("수정하려는 게시글 번호 입력");
		System.out.print(">>>");
		
		Scanner inputInt = new Scanner(System.in);
		
		int number = inputInt.nextInt();
		
		boardDAO.modify(number, session);
		System.out.println("=========끝========");
	}

	private void readOne(Scanner inputStr, MemberDTO session) throws SQLException {
		// 제목을 입력하면 내용이 보이도록 select처리
		
		selectAll();
		System.out.println("보고싶은 게시글의 번호를 입력하세요.");
		System.out.print(">>>");
		
		Scanner inputInt = new Scanner(System.in);
		
		int number = inputInt.nextInt();
		
		boardDAO.readOne(number);
		printComment(number);
		
		insertComment(number, inputInt, session);
				
		System.out.println("==========readOne끝==========");
	}
	
	private void printComment(int number) throws SQLException {
		
		System.out.println("==========댓글==========");
		
		CommentDAO commentDAO = new CommentDAO();
		commentDAO.prntComment(number);
		
		System.out.println("==========printComment끝==========");
		
	}

	private void insertComment(int boardNum, Scanner inputStr, MemberDTO session) throws SQLException {
		
		System.out.println("댓글을 입력하시겠습니까 (0: 댓글 작성, 이외숫자: 나가기)");
		
		String insertCommentumber = inputStr.next();
		
		if(insertCommentumber.equals("0")) {
			
			CommentService commentService = new CommentService();
			commentService.subMenu(boardNum, inputStr, session);
		}
		
		System.out.println("==========insertComment끝==========");
	}

	private void insertBoard(MemberDTO session) throws SQLException {
		
		if(session == null) {
			
			System.out.println("로그인 중이 아닙니다.");
			return;
		}
		//키보드로 입력한 데이터를 dto 사용하여 데이터베이스에 insert하자
		BoardDTO boardDTO = new BoardDTO();

		boardDTO.setBwriter(session.getId());
		
		Scanner nextLineInput = new Scanner(System.in);
		
		System.out.println("제목 : ");
		boardDTO.setBtitle(nextLineInput.nextLine());	
		
		System.out.println("내용 : ");
		boardDTO.setBcontent(nextLineInput.nextLine());	//띄어쓰기 nextline
		
		boardDAO.insertBoard(boardDTO);
		System.out.println("=========insertBoard종료=========");
	}

	private void selectAll()throws SQLException {
		//전제보기
		System.out.println("================");
		System.out.println("====게시판 목록====");
		boardDAO.selectAll();
		System.out.println("================");
	}
	
}
