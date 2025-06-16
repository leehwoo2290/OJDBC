package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.MemberDAO;
import mbcboard.dto.MemberDTO;

public class MemberService {

	
	public MemberDAO memberDAO = new MemberDAO(); // 1,2단계실행
	
	public MemberDTO subMenu(Scanner inputStr, MemberDTO session) throws SQLException{
		
	boolean bSubRun = true;
		
		while(bSubRun) {
			
			System.out.println("회원 서비스 시작");
			System.out.println("1. 회원 가입");
			System.out.println("2. 전체 회원 열람(관리자 전용)");
			System.out.println("3. 로그인");
			System.out.println("4. 회원 정보 수정");
			System.out.println("5. 회원 탈퇴");
			System.out.println("6. 나가기");
			System.out.println(">>>");
			
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			
			case "1":
				System.out.println("회원 가입");
				joinMember(inputStr);
				break;
			case "2":
				System.out.println("전체 회원 열람(관리자 전용)");
				readAllMember(session);
				break;
			case "3":
				System.out.println("로그인");
				session = logInMember(inputStr);
				break;
			case "4":
				System.out.println("회원 정보 수정");
				session = modifyMember(inputStr, session);
				break;
			case "5":
				System.out.println("회원 탈퇴");
				session = deleteMember(session);
				break;
			case "6":
				System.out.println("회원 설정 종료");
				memberDAO.connection.close();
				bSubRun = false;
				break;
			default:
				System.out.println("잘못된 입력 값");
				break;
			}
		}
		
		return session;
	}

	private MemberDTO deleteMember(MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("로그인 상태가 아닙니다.");
			return null;
		}
		
		System.out.println("==========================");
		System.out.println("회원 탈퇴 시작\n");
		
		memberDAO.deleteMember(session);
		System.out.println("=========deleteMember종료=========");
		return null;
	}

	private MemberDTO modifyMember(Scanner inputStr, MemberDTO session) throws SQLException {

		if(session == null) {
			
			System.out.println("로그인 상태가 아닙니다.");
			return null;
		}
		
		System.out.println("==========================");
		System.out.println("회원 정보 수정 시작\n");
		
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("닉네임 : ");
		memberDTO.setBwriter(inputStr.next());
		
		System.out.println("PW : ");
		memberDTO.setPw(inputStr.next());	//띄어쓰기 nextline
		
		return memberDAO.modifyMember(session, memberDTO);
		
	}

	private MemberDTO logInMember(Scanner inputStr) throws SQLException {
		
		System.out.println("==========================");
		System.out.println("로그인 시작\n");
		
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("ID : ");
		memberDTO.setId(inputStr.next());
		
		System.out.println("PW : ");
		memberDTO.setPw(inputStr.next());
		return memberDAO.logInMember(memberDTO);	
	}

	private void readAllMember(MemberDTO session) throws SQLException {
		
		if(session == null || !session.getId().equals("admin")) {
			
			System.out.println("로그인 중이 아니거나 admin 계정이 아닙니다.\n");
			return;
		}
		System.out.println("==========================");
		System.out.println("전체 회원 열람 시작\n");
		
		memberDAO.readAllMember();	
		System.out.println("=========readAllMember종료=========");
	}

	private void joinMember(Scanner inputStr) throws SQLException {
		
		System.out.println("==========================");
		System.out.println("회원 가입 시작\n");
		
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("닉네임 : ");
		memberDTO.setBwriter(inputStr.next());
		
		System.out.println("6글자 이상");
		System.out.println("ID : ");
		memberDTO.setId(inputStr.next());
		
		System.out.println("8글자 이상, 대문자 소문자 숫자 특수문자 포함");
		System.out.println("PW : ");
		memberDTO.setPw(inputStr.next());	//띄어쓰기 nextline
		
		memberDAO.joinMember(memberDTO);	
		System.out.println("=========joinMember종료=========");	
		
	}
	
}
