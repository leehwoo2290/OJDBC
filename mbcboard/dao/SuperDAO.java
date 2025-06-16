package mbcboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mbcboard.dto.BoardDTO;

public class SuperDAO {

	//�Խ����� db�� ������ ����Ѵ�.
		//jdbc 5�ܰ踦 ���
		//1�ܰ� : connect ��ü�� ����� ojdbc6.jar ����
		//2�ܰ�: url, id , pw, sql ������ �ۼ�
		//3�ܰ�: ������ ����
		//4�ܰ�: ������ ���� ���
		//5�ܰ�: ���� ����
		
		public BoardDTO boardDTO = new BoardDTO();
		public Connection connection = null;					//1�ܰ�
		public Statement statement = null;						//3�ܰ�(����)
		public PreparedStatement preparedStatement = null;		//3�ܰ�(����)
		public ResultSet resultSet = null;						//4�ܰ� ��� �޴� ��ü
		public int result = 0;									//4�ܰ� ��� ���� ����
		
		public SuperDAO() {
			
			try {
				//���� �߻��ص� ���α׷� �������� ����
				
				//1�ܰ� ojdbc6.jar ȣ��
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//2�ܰ�
				connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.151:1521:xe",
						"boardtest", "boardtest");
				
			}catch(ClassNotFoundException e) {
				
				System.out.println("����̹� �̸��̳�, ojdbc6.jar ������ �߸��Ǿ����ϴ�.");
				e.printStackTrace();
				System.exit(0); //��������
			}catch(SQLException e) {
				
				System.out.println("url, id, pw�� �߸��Ǿ����ϴ�.");
				e.printStackTrace();
				System.exit(0); //��������
			}
		}
}
