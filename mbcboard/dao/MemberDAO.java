package mbcboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Scanner;

import mbcboard.dto.BoardDTO;
import mbcboard.dto.MemberDTO;

public class MemberDAO extends SuperDAO {
	
	//ȸ������
	public void joinMember(MemberDTO memberDTO) throws SQLException {		

		try {
			
			String sql = "insert into member(mno, bwriter, id, pw)" + " values(member_seq.nextVal, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getBwriter());
			preparedStatement.setString(2, memberDTO.getId());
			preparedStatement.setString(3, memberDTO.getPw());
			result = preparedStatement.executeUpdate();
			
			if(result > 0) {
				
				System.out.println(result + " ȸ�� ���� ����");
				connection.commit();
			}else {
				
				System.out.println(result + " ���� ���� ���, ȸ�� ���� ����");
				connection.rollback();
			}
			
		} catch (SQLException e) {
			
			System.out.println("���ܹ߻� joinMemberȮ��");
			e.printStackTrace();
			
		}finally {
			//���� �߻� �� ���� ���� �� ������ ó����
			
			preparedStatement.close();
			//connection.close();		
		}	
		
	}

	//��ü ȸ�� ����
	public void readAllMember() throws SQLException {
		
		try {
			
			String sql = "select mno, bwriter, id, regidate, activate from member order by mno desc";
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery(); //������ ���� �� ��� ǥ�� ����
			
			while(resultSet.next()) {
				//�˻� ����� ������
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setMno(resultSet.getInt("Mno"));
				memberDTO.setBwriter(resultSet.getString("bwriter"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setRegidate(resultSet.getDate("regidate"));
				memberDTO.setActivate(resultSet.getString("activate"));
		
				//������ ���̽��� �ִ� �� ��ü�� �ֱ� �Ϸ�
				
				System.out.println("==========================");
				System.out.println("��ȣ : " + memberDTO.getMno());
				System.out.println("�г��� : " + memberDTO.getBwriter());
				System.out.println("ID : " + memberDTO.getId());
				System.out.println("������ : " + memberDTO.getRegidate());	
				System.out.println("���� Ȱ��ȭ ���� : " + memberDTO.getActivate());		
			}
			
		} catch (SQLException e) {
			
			System.out.println("���ܹ߻� readAllMemberȮ��");
			e.printStackTrace();
			
		}finally {
			//���� �߻� �� ���� ���� �� ������ ó����
			
			preparedStatement.close();
			resultSet.close();		
		}	
		
	}

	//�α���
	public MemberDTO logInMember(MemberDTO memberDTO) throws SQLException {
		
		MemberDTO session = null;
		
		try {
			
			String sql = "select * from member where id= ? and pw=? and activate='Y'";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getId());
			preparedStatement.setString(2, memberDTO.getPw());

			//System.out.println("����Ȯ�� " + sql);
			
			resultSet = preparedStatement.executeQuery(); //������ ���� �� ��� ǥ�� ����
			
			if(resultSet.next()) {
				
				session = new MemberDTO();
				session.setMno(resultSet.getInt("Mno"));
				session.setBwriter(resultSet.getString("bwriter"));
				session.setId(resultSet.getString("id"));
				session.setPw(resultSet.getString("pw"));
				session.setRegidate(resultSet.getDate("regidate"));
				session.setActivate(resultSet.getString("activate"));
				System.out.println(result + "�α��� ����");
			}else {
				
				System.out.println(result + " ���� ���� ���, �α��� ����");
			
			}
			
		} catch (SQLException e) {
			
			System.out.println("���ܹ߻� logInMemberȮ��");
			e.printStackTrace();
			
		}finally {
			//���� �߻� �� ���� ���� �� ������ ó����
			
			preparedStatement.close();
			resultSet.close();		
		}	

		return session;
	}

	//ȸ�� ���� ����
	public MemberDTO modifyMember(MemberDTO session, MemberDTO memberDTO) throws SQLException {
		
		try {
			String sql = "update member set bwriter=?, pw=? where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getBwriter());
			preparedStatement.setString(2, memberDTO.getPw());
			preparedStatement.setString(3, session.getId());
			
			result = preparedStatement.executeUpdate(); //������ ���� ��� ����
			
			if(result > 0) {
				
				session.setBwriter(memberDTO.getBwriter());
				session.setPw(memberDTO.getPw());

				System.out.println(result + " ȸ�� ���� ���� �Ϸ�");
				connection.commit();
			}else {
				
				System.out.println(result + " ȸ�� ���� ���� ����");
				connection.rollback();
			}
			
		} catch (SQLException e) {
			System.out.println("���� �߻� modifyMember �޼��� sql�� Ȯ��");
			e.printStackTrace();
		}finally {
			preparedStatement.close();
			
		}
		
		return session;
	}

	//ȸ�� Ż��
	public void deleteMember(MemberDTO session) throws SQLException {
		
		try {
			//String sql = "delete from member where id =?";
			String sql = "update member set activate='N' where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, session.getId());
			
			result = preparedStatement.executeUpdate();
			
			if(result > 0) {
				System.out.println(result + "������ ���� �Ǿ����ϴ�");
				connection.commit();	
				
			}else {
				
				System.out.println(result + " ���� ���� ����");
				connection.rollback();
			}
			
			System.out.println("==================");
			
			
		} catch (SQLException e) {

			System.out.println("���� �߻� deleteOne sql�� Ȯ��");
			e.printStackTrace();
		} finally {
			
			preparedStatement.close();
		}
		
	}

}
