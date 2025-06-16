package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.BoardDAO;
import mbcboard.dao.CommentDAO;
import mbcboard.dto.BoardDTO;
import mbcboard.dto.CommentDTO;
import mbcboard.dto.MemberDTO;

public class BoardService {
	//dao dto ����Ͽ� �θ޴��� crudó��
	
	public BoardDAO boardDAO = new BoardDAO(); // 1,2�ܰ����
	
	public void subMenu(Scanner inputStr, MemberDTO session) throws SQLException {
		
		boolean bSubRun = true;
		
		while(bSubRun) {
			
			System.out.println("�Խ��� ���� ����");
			System.out.println("1. ��κ���");
			System.out.println("2. �Խñ� �ۼ�");
			System.out.println("3. �Խñ� �ڼ��� ����");
			System.out.println("4. �� �Խù� ����");
			System.out.println("5. �Խñ� ����");
			System.out.println("6. �Խñ� ����");
			System.out.println("7. ������");
			System.out.println(">>>");
			
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			
			case "1":
				System.out.println("��� �Խù� ����");
				selectAll();
				break;
			case "2":
				System.out.println("�Խù� �ۼ�");
				insertBoard(session);
				break;
			case "3":
				System.out.println("�Խù� �ڼ��� ����");
				readOne(inputStr, session);
				break;
			case "4":
				System.out.println("�� �Խù� ����");
				readBySession(inputStr, session);
				break;
			case "5":
				System.out.println("�Խù� ����");
				modify(session);
				break;
			case "6":
				System.out.println("�Խù� ����");
				deleteOne(inputStr, session);
				break;
			case "7":
				System.out.println("�Խù� ���� ����");
				boardDAO.connection.close();
				bSubRun = false;
				break;
			default:
				System.out.println("�߸��� �Է� ��");
				break;
			}
		}	
	}

	private void readBySession(Scanner inputStr, MemberDTO session) throws SQLException {

		// �α����� ������ �ۼ��� �۵��� ��ȣ�� �ش� ���� ã�Ƽ� ������ �����Ѵ�	
		if(session == null) {
			
			System.out.println("�α��� ���� �ƴմϴ�.");
			return;
		}
		
		System.out.println("���� �ۼ��� �Խñ� ���");
		System.out.println("==================");
		
		boardDAO.readBySession(session);
		
		System.out.println("=========��========");
	}

	private void deleteOne(Scanner inputStr, MemberDTO session) throws SQLException {
		
		// �α����� ������ �ۼ��� �۵��� ��ȣ�� �ش� ���� ã�Ƽ� ������ �����Ѵ�	
		if(session == null) {
					
			System.out.println("�α��� ���� �ƴմϴ�.");
			return;
		}
		// �Խù��� ��ȣ�� �޾� �����Ѵ�
		System.out.println("�����Ϸ��� �Խñ��� ��ȣ�� �Է��ϼ���");
		Scanner inputInt = new Scanner(System.in);
		System.out.print(">>>");
		int selectBno = inputInt.nextInt();
		boardDAO.deleteOne(selectBno, session);
		
	}

	private void modify(MemberDTO session) throws SQLException {
		
		// �α����� ������ �ۼ��� �۵��� ��ȣ�� �ش� ���� ã�Ƽ� ������ �����Ѵ�	
		if(session == null) {
			
			System.out.println("�α��� ���� �ƴմϴ�.");
			return;
		}

		boardDAO.readBySession(session);
		
		System.out.println("�����Ϸ��� �Խñ� ��ȣ �Է�");
		System.out.print(">>>");
		
		Scanner inputInt = new Scanner(System.in);
		
		int number = inputInt.nextInt();
		
		boardDAO.modify(number, session);
		System.out.println("=========��========");
	}

	private void readOne(Scanner inputStr, MemberDTO session) throws SQLException {
		// ������ �Է��ϸ� ������ ���̵��� selectó��
		
		selectAll();
		System.out.println("������� �Խñ��� ��ȣ�� �Է��ϼ���.");
		System.out.print(">>>");
		
		Scanner inputInt = new Scanner(System.in);
		
		int number = inputInt.nextInt();
		
		boardDAO.readOne(number);
		printComment(number);
		
		insertComment(number, inputInt, session);
				
		System.out.println("==========readOne��==========");
	}
	
	private void printComment(int number) throws SQLException {
		
		System.out.println("==========���==========");
		
		CommentDAO commentDAO = new CommentDAO();
		commentDAO.prntComment(number);
		
		System.out.println("==========printComment��==========");
		
	}

	private void insertComment(int boardNum, Scanner inputStr, MemberDTO session) throws SQLException {
		
		System.out.println("����� �Է��Ͻðڽ��ϱ� (0: ��� �ۼ�, �ܼ̿���: ������)");
		
		String insertCommentumber = inputStr.next();
		
		if(insertCommentumber.equals("0")) {
			
			CommentService commentService = new CommentService();
			commentService.subMenu(boardNum, inputStr, session);
		}
		
		System.out.println("==========insertComment��==========");
	}

	private void insertBoard(MemberDTO session) throws SQLException {
		
		if(session == null) {
			
			System.out.println("�α��� ���� �ƴմϴ�.");
			return;
		}
		//Ű����� �Է��� �����͸� dto ����Ͽ� �����ͺ��̽��� insert����
		BoardDTO boardDTO = new BoardDTO();

		boardDTO.setBwriter(session.getId());
		
		Scanner nextLineInput = new Scanner(System.in);
		
		System.out.println("���� : ");
		boardDTO.setBtitle(nextLineInput.nextLine());	
		
		System.out.println("���� : ");
		boardDTO.setBcontent(nextLineInput.nextLine());	//���� nextline
		
		boardDAO.insertBoard(boardDTO);
		System.out.println("=========insertBoard����=========");
	}

	private void selectAll()throws SQLException {
		//��������
		System.out.println("================");
		System.out.println("====�Խ��� ���====");
		boardDAO.selectAll();
		System.out.println("================");
	}
	
}
