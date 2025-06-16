package mbcboard.dao;

import java.sql.SQLException;

import mbcboard.dto.MemberDTO;

public class CommentDAO extends SuperDAO {

	public void insert(int boardNum, MemberDTO session, String commentContent) throws SQLException {
		
		// sql ����Ͽ� ��ü ��� ���� ���
			try {
					
				String sql = "insert into boardcomment(cno, cwriter, ccontent)" + " values(?, ?, ?)";
					
			
					preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, boardNum);
				preparedStatement.setString(2, session.getId());
				preparedStatement.setString(3, commentContent);
				result = preparedStatement.executeUpdate();
				if(result > 0) {
					
					System.out.println(result + " ��� �߰� ����");
					connection.commit();
				}else {
					
					System.out.println(result + " ���� ���� ���, ��� �߰� ����");
					connection.rollback();
				}
					
				
				}catch(SQLException e) {
					//�����߻� �� ����ó��
					System.out.println("insert()�޼��忡 �������� �߸� �Ǿ����ϴ�.");
					e.printStackTrace();
				}finally {
					//�׻� ����
				
					preparedStatement.close();
					//connection.close();
				}	
		
	}

	public void prntComment(int number) throws SQLException {
		
		// sql ����Ͽ� ��ü ��� ���� ���
			try {
					
				String sql = "select cno, cwriter, ccontent, cdate from boardcomment where activate = 'Y' and cno=? order by cdate desc";
			
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, number);
				resultSet = preparedStatement.executeQuery(); //������ ���� �� ��� ǥ�� ����
					
				System.out.println("�Խñ� ��ȣ\t �ۼ���\t �ۼ���\t\t ����\t");
				//resultSet-> ���ǥ (���̺� ����)
				while(resultSet.next()) {
					//���ǥ ������ �Ʒ��� �������
					System.out.print(resultSet.getInt("cno") + "\t");
					System.out.print(resultSet.getString("cwriter") + "\t");
					System.out.print(resultSet.getDate("cdate") + "\t");
					System.out.println(resultSet.getString("ccontent") + "\t");
				}
			}catch(SQLException e) {
				//�����߻� �� ����ó��
				System.out.println("selectAll()�޼��忡 �������� �߸� �Ǿ����ϴ�.");
				e.printStackTrace();
			}finally {
				//�׻� ����
				resultSet.close();
				preparedStatement.close();				
			}
		
	}

	public void delete(int boardNum, MemberDTO session) throws SQLException {
		
		try {
			String sql = "update boardcomment set activate='N' where cno=? and cwriter=? and activate='Y'";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, boardNum);
			preparedStatement.setString(2, session.getId());
			
			result = preparedStatement.executeUpdate();
			
			if(result > 0) {
				System.out.println(result + "����� ���� �Ǿ����ϴ�");
				connection.commit();	
				
			}else {
				
				System.out.println(result + " ��� ���� ����");
				connection.rollback();
			}
			
			System.out.println("==================");
			prntComment(boardNum); //���� �� ��ü ����Ʈ ����
			
			
		} catch (SQLException e) {

			System.out.println("���� �߻� deleteOne sql�� Ȯ��");
			e.printStackTrace();
		} finally {
			
			preparedStatement.close();
		}
		
	}

}
