package mbcboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import mbcboard.dto.BoardDTO;
import mbcboard.dto.MemberDTO;

public class BoardDAO extends SuperDAO {

	public void selectAll() throws SQLException{ //������ ���� ����ó��
		// sql ����Ͽ� ��ü ��� ���� ���
		try {
			
			String sql = "select bno, btitle, bwriter, bdate from board where activate = 'Y' order by bdate desc";
			statement = connection.createStatement(); //������ ���� �� ��ü ����
			resultSet = statement.executeQuery(sql); //������ �����ؼ� ��� �޾ƿ�
			
			System.out.println("��ȣ\t ����\t �ۼ���\t ���̵�\t �ۼ���\t");
			//resultSet-> ���ǥ (���̺� ����)
			while(resultSet.next()) {
				//���ǥ ������ �Ʒ��� �������
				System.out.print(resultSet.getInt("bno") + "\t");
				System.out.print(resultSet.getString("btitle") + "\t");
				System.out.print(resultSet.getString("bwriter") + "\t");
				System.out.println(resultSet.getDate("bdate") + "\t");
			}
			
			System.out.println("========��=======");
		}catch(SQLException e) {
			//�����߻� �� ����ó��
			System.out.println("selectAll()�޼��忡 �������� �߸� �Ǿ����ϴ�.");
			e.printStackTrace();
		}finally {
			//�׻� ����
			resultSet.close();
			statement.close();
			//connection.close();
		}
	}

	public void insertBoard(BoardDTO boardDTO) throws SQLException {
		// jdbc��� insert���� ó��
		// preparedStatement �� ���
		// ���������� ?�ڸ��� ���ͷ� �� ����
		
		try {
			String sql = "insert into board(bno, btitle, bcontent, bwriter)" +
					" values(board_seq.nextVal, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle());
			preparedStatement.setString(2, boardDTO.getBcontent());
			preparedStatement.setString(3, boardDTO.getBwriter());
			
			System.out.println("����Ȯ�� " + sql);
			
			result = preparedStatement.executeUpdate();
			
			if(result > 0) {
				
				System.out.println(result + "���� �Խù��� ��� �Ǿ� �ֽ��ϴ�.");
				connection.commit();
			}else {
				
				System.out.println(result + " ���� ���� ���, �Է� ����");
				connection.rollback();
			}
			
		}catch(SQLException e) {
			
			System.out.println("insertBoard ������ ����");
			e.printStackTrace();
		}finally {
			//���� �߻� �� ���� ���� �� ������ ó����
			
			preparedStatement.close();
			//connection.close();
			
		}
		
	}

	public void readOne(int number) throws SQLException {
		//���� ���ڿ� �޾Ƽ� select ���
		
		try {
			
			String sql = "select bno, btitle, bcontent, bwriter, bdate " +
			"from board where bno= ? and activate= 'Y'";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, number); //service���� �Ѿ�� ã�� ���� ������ ?�� �Ѿ��.
			resultSet = preparedStatement.executeQuery(); //������ ���� �� ��� ǥ�� ����
			
			if(resultSet.next()) {
				//�˻� ����� ������
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setBno(resultSet.getInt("bno"));
				boardDTO.setBtitle(resultSet.getString("btitle"));
				boardDTO.setBcontent(resultSet.getString("bcontent"));
				boardDTO.setBwriter(resultSet.getString("bwriter"));
				boardDTO.setBdate(resultSet.getDate("bdate"));
				//������ ���̽��� �ִ� �� ��ü�� �ֱ� �Ϸ�
				
				System.out.println("==========================");
				System.out.println("��ȣ : " + boardDTO.getBno());
				System.out.println("���� : " + boardDTO.getBtitle());
				System.out.println("���� : " + boardDTO.getBcontent());
				System.out.println("�ۼ��� : " + boardDTO.getBwriter());
				System.out.println("�ۼ��� : " + boardDTO.getBdate());
				
			}else {
				
				System.out.println("�ش� �Խù��� �������� �ʽ��ϴ�");
			}
			
		} catch (SQLException e) {

			System.out.println("���ܹ߻� readoneȮ��");
			e.printStackTrace();
		} finally {
			
			resultSet.close();
			preparedStatement.close();
		}
		
	}

	public void modify(int number, MemberDTO session) throws SQLException {
		// ������ ã�Ƽ� ���� ����
		BoardDTO boardDTO = new BoardDTO();
		
		Scanner nextLineInput = new Scanner(System.in);
		
		System.out.println("������ ���� �Է�");
		System.out.println("����: ");
		boardDTO.setBtitle(nextLineInput.nextLine());
		
		System.out.println("����: ");
		boardDTO.setBcontent(nextLineInput.nextLine());
		
		try {
			String sql = "update board set btitle=?, bcontent=?, bdate= sysdate where bno=? and bwriter=? and activate = 'Y'";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle());
			preparedStatement.setString(2, boardDTO.getBcontent());
			preparedStatement.setInt(3, number);
			preparedStatement.setString(4, session.getId());
			
			result = preparedStatement.executeUpdate(); //������ ���� ��� ����
			
			if(result > 0) {
				
				System.out.println(result + " �����Ͱ� ���� �Ǿ����ϴ�");
				connection.commit();
			}else {
				
				System.out.println(result + " �������� �ʾҽ��ϴ�.");
				connection.rollback();
			}
			
		} catch (SQLException e) {
			System.out.println("���� �߻� modify �޼��� sql�� Ȯ��");
			e.printStackTrace();
		}finally {
			preparedStatement.close();
		}
	}

	public void deleteOne(int selectBno, MemberDTO session) throws SQLException {
		// ���񽺿��� ���� �Խù��� ��ȣ�� �̿��Ͽ� �����͸� �����Ѵ�.
		
		try {
			String sql = "update board set activate='N' where bno=? and bwriter=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, selectBno);
			preparedStatement.setString(2, session.getId());
			
			result = preparedStatement.executeUpdate();
			
			if(result > 0) {
				System.out.println(result + "�Խù��� ���� �Ǿ����ϴ�");
				connection.commit();	
				
			}else {
				
				System.out.println(result + " �Խù� ���� ����");
				connection.rollback();
			}
			
			System.out.println("==================");
			selectAll(); //���� �� ��ü ����Ʈ ����
			
			
		} catch (SQLException e) {

			System.out.println("���� �߻� deleteOne sql�� Ȯ��");
			e.printStackTrace();
		} finally {
			
			preparedStatement.close();
		}
		
	}

	public void readBySession(MemberDTO session) throws SQLException {
		
		try {
			//�ܷ�Ű ����� ������µ� �׳ɽẽ
			String sql = "select b.bno, b.btitle, b.bwriter, b.bdate from member m inner join board b on m.id = b.bwriter where m.id= ?";
			//String sql = "select bno, btitle, bwriter, bdate from board where bwriter= ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, session.getId()); //service���� �Ѿ�� ã�� ���� ������ ?�� �Ѿ��.
			resultSet = preparedStatement.executeQuery(); //������ ���� �� ��� ǥ�� ����
			
			while(resultSet.next()) {
				//���ǥ ������ �Ʒ��� �������
				System.out.print(resultSet.getInt("bno") + "\t");
				System.out.print(resultSet.getString("btitle") + "\t");
				System.out.print(resultSet.getString("bwriter") + "\t");
				System.out.println(resultSet.getDate("bdate") + "\t");
			}
			
			
		} catch (SQLException e) {

			System.out.println("���ܹ߻� readBySessionȮ��");
			e.printStackTrace();
		} finally {
			
			resultSet.close();
			preparedStatement.close();
		}
		
	}
}
