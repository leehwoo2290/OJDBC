package mbcboard.dao;

import java.sql.SQLException;

import mbcboard.dto.MemberDTO;

public class CommentDAO extends SuperDAO {

	public void insert(int boardNum, MemberDTO session, String commentContent) throws SQLException {
		
		// sql 사용하여 전체 목록 보기 출력
			try {
					
				String sql = "insert into boardcomment(cno, cwriter, ccontent)" + " values(?, ?, ?)";
					
			
					preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, boardNum);
				preparedStatement.setString(2, session.getId());
				preparedStatement.setString(3, commentContent);
				result = preparedStatement.executeUpdate();
				if(result > 0) {
					
					System.out.println(result + " 댓글 추가 성공");
					connection.commit();
				}else {
					
					System.out.println(result + " 쿼리 실행 결과, 댓글 추가 실패");
					connection.rollback();
				}
					
				
				}catch(SQLException e) {
					//오류발생 시 예외처리
					System.out.println("insert()메서드에 쿼리문이 잘못 되었습니다.");
					e.printStackTrace();
				}finally {
					//항상 실행
				
					preparedStatement.close();
					//connection.close();
				}	
		
	}

	public void prntComment(int number) throws SQLException {
		
		// sql 사용하여 전체 목록 보기 출력
			try {
					
				String sql = "select cno, cwriter, ccontent, cdate from boardcomment where activate = 'Y' and cno=? order by cdate desc";
			
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, number);
				resultSet = preparedStatement.executeQuery(); //쿼리문 실행 후 결과 표로 받음
					
				System.out.println("게시글 번호\t 작성자\t 작성일\t\t 내용\t");
				//resultSet-> 결과표 (테이블 값들)
				while(resultSet.next()) {
					//결과표 위부터 아래로 순차출력
					System.out.print(resultSet.getInt("cno") + "\t");
					System.out.print(resultSet.getString("cwriter") + "\t");
					System.out.print(resultSet.getDate("cdate") + "\t");
					System.out.println(resultSet.getString("ccontent") + "\t");
				}
			}catch(SQLException e) {
				//오류발생 시 예외처리
				System.out.println("selectAll()메서드에 쿼리문이 잘못 되었습니다.");
				e.printStackTrace();
			}finally {
				//항상 실행
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
				System.out.println(result + "댓글이 삭제 되었습니다");
				connection.commit();	
				
			}else {
				
				System.out.println(result + " 댓글 삭제 실패");
				connection.rollback();
			}
			
			System.out.println("==================");
			prntComment(boardNum); //삭제 후 전체 리스트 보기
			
			
		} catch (SQLException e) {

			System.out.println("예외 발생 deleteOne sql문 확인");
			e.printStackTrace();
		} finally {
			
			preparedStatement.close();
		}
		
	}

}
