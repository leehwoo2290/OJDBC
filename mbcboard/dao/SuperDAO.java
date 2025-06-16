package mbcboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mbcboard.dto.BoardDTO;

public class SuperDAO {

	//게시판의 db와 연동을 담당한다.
		//jdbc 5단계를 사용
		//1단계 : connect 객체를 사용해 ojdbc6.jar 생성
		//2단계: url, id , pw, sql 쿼리문 작성
		//3단계: 쿼리문 실행
		//4단계: 쿼리문 실행 결과
		//5단계: 연결 종료
		
		public BoardDTO boardDTO = new BoardDTO();
		public Connection connection = null;					//1단계
		public Statement statement = null;						//3단계(구형)
		public PreparedStatement preparedStatement = null;		//3단계(신형)
		public ResultSet resultSet = null;						//4단계 결과 받는 객체
		public int result = 0;									//4단계 결과 저장 정수
		
		public SuperDAO() {
			
			try {
				//예외 발생해도 프로그램 강제종료 안함
				
				//1단계 ojdbc6.jar 호출
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//2단계
				connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.151:1521:xe",
						"boardtest", "boardtest");
				
			}catch(ClassNotFoundException e) {
				
				System.out.println("드라이버 이름이나, ojdbc6.jar 파일이 잘못되었습니다.");
				e.printStackTrace();
				System.exit(0); //강제종료
			}catch(SQLException e) {
				
				System.out.println("url, id, pw가 잘못되었습니다.");
				e.printStackTrace();
				System.exit(0); //강제종료
			}
		}
}
