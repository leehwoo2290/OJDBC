package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.MemberDAO;
import mbcboard.dto.MemberDTO;

public class MemberService {

	
	public MemberDAO memberDAO = new MemberDAO(); // 1,2�ܰ����
	
	public MemberDTO subMenu(Scanner inputStr, MemberDTO session) throws SQLException{
		
	boolean bSubRun = true;
		
		while(bSubRun) {
			
			System.out.println("ȸ�� ���� ����");
			System.out.println("1. ȸ�� ����");
			System.out.println("2. ��ü ȸ�� ����(������ ����)");
			System.out.println("3. �α���");
			System.out.println("4. ȸ�� ���� ����");
			System.out.println("5. ȸ�� Ż��");
			System.out.println("6. ������");
			System.out.println(">>>");
			
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			
			case "1":
				System.out.println("ȸ�� ����");
				joinMember(inputStr);
				break;
			case "2":
				System.out.println("��ü ȸ�� ����(������ ����)");
				readAllMember(session);
				break;
			case "3":
				System.out.println("�α���");
				session = logInMember(inputStr);
				break;
			case "4":
				System.out.println("ȸ�� ���� ����");
				session = modifyMember(inputStr, session);
				break;
			case "5":
				System.out.println("ȸ�� Ż��");
				session = deleteMember(session);
				break;
			case "6":
				System.out.println("ȸ�� ���� ����");
				memberDAO.connection.close();
				bSubRun = false;
				break;
			default:
				System.out.println("�߸��� �Է� ��");
				break;
			}
		}
		
		return session;
	}

	private MemberDTO deleteMember(MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("�α��� ���°� �ƴմϴ�.");
			return null;
		}
		
		System.out.println("==========================");
		System.out.println("ȸ�� Ż�� ����\n");
		
		memberDAO.deleteMember(session);
		System.out.println("=========deleteMember����=========");
		return null;
	}

	private MemberDTO modifyMember(Scanner inputStr, MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("�α��� ���°� �ƴմϴ�.");
			return null;
		}
		
		System.out.println("==========================");
		System.out.println("ȸ�� ���� ���� ����\n");
		
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("�г��� : ");
		memberDTO.setBwriter(inputStr.next());
		
		System.out.println("PW : ");
		memberDTO.setPw(inputStr.next());	//���� nextline
		
		return memberDAO.modifyMember(session, memberDTO);
		
	}

	private MemberDTO logInMember(Scanner inputStr) throws SQLException {
		
		System.out.println("==========================");
		System.out.println("�α��� ����\n");
		
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("ID : ");
		memberDTO.setId(inputStr.next());
		
		System.out.println("PW : ");
		memberDTO.setPw(inputStr.next());
		return memberDAO.logInMember(memberDTO);	
	}

	private void readAllMember(MemberDTO session) throws SQLException {
		
		if(session == null || !session.getId().equals("admin")) {
			
			System.out.println("�α��� ���� �ƴϰų� admin ������ �ƴմϴ�.\n");
			return;
		}
		System.out.println("==========================");
		System.out.println("��ü ȸ�� ���� ����\n");
		
		memberDAO.readAllMember();	
		System.out.println("=========readAllMember����=========");
	}

	private void joinMember(Scanner inputStr) throws SQLException {
		
		System.out.println("==========================");
		System.out.println("ȸ�� ���� ����\n");
		
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("�г��� : ");
		memberDTO.setBwriter(inputStr.next());
		
		System.out.println("6���� �̻�");
		System.out.println("ID : ");
		memberDTO.setId(inputStr.next());
		
		System.out.println("8���� �̻�, �빮�� �ҹ��� ���� Ư������ ����");
		System.out.println("PW : ");
		memberDTO.setPw(inputStr.next());	//���� nextline
		
		memberDAO.joinMember(memberDTO);	
		System.out.println("=========joinMember����=========");	
		
	}
	
}
