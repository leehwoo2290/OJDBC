package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.CommentDAO;
import mbcboard.dto.MemberDTO;

public class CommentService {

	public CommentDAO commentDAO = new CommentDAO(); // 1,2�ܰ����
	
	public void subMenu(int boardNum, Scanner inputStr, MemberDTO session) throws SQLException {
		
		boolean bSubRun = true;
		
		while(bSubRun) {
			
			System.out.println("��� ���� ����");
			System.out.println("1. ��� �ۼ�");
			System.out.println("2. ��� ����");
			System.out.println("3. ������");

			System.out.println(">>>");
			
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			
			case "1":
				System.out.println("��� �ۼ�");
				insert(boardNum, session);
				break;
			case "2":
				System.out.println("��� ����");
				delete(boardNum, session);
				break;
			case "3":
				System.out.println("��� ���� ����");
				commentDAO.connection.close();
				bSubRun = false;
				break;
			default:
				System.out.println("�߸��� �Է� ��");
				break;
			}
		}
		
	}

	private void delete(int boardNum, MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("�α��� ���°� �ƴմϴ�.");
			return;
		}
		
		System.out.println("==========================");
		System.out.println("��� ����\n");
		
		commentDAO.delete(boardNum, session);
	}

	private void insert(int boardNum, MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("�α��� ���°� �ƴմϴ�.");
			return;
		}
		
		System.out.println("==========================");
		System.out.println("��� �߰�\n");
		
		Scanner inputNextLine = new Scanner(System.in);
		
		String commentContent = inputNextLine.nextLine();
		
		commentDAO.insert(boardNum, session, commentContent);
	}
}
