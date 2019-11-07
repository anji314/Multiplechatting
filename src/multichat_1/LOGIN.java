package multichat_1;
import java.sql.*;


public class LOGIN {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버
	private final String DB_URL = "jdbc:mysql://localhost:3306/talkdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //접속할 DB 서버
		
	private final String USER_NAME = "root"; //DB에 접속할 사용자 이름을 상수로 정의
	private final String PASSWORD = "anji314"; //사용자의 비밀번호를 상수로 정의
	int cnt=0;
	public String userid;
	public LOGIN(String userid) {
		Connection conn =null;
		Statement state =null;
		try {
			//System.out.println("MYSQL open");
			Class.forName(JDBC_DRIVER);
			conn=DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
			//System.out.println("[mysql connetion ]\n");
			state=conn.createStatement();
			
			String sql;
			sql="SELECT * FROM user_id where ID='"+userid+"'";
			ResultSet rs=state.executeQuery(sql); 
			//System.out.println(userid+"이걸로 로그인\n");
			while(rs.next()) {
				String ID=rs.getString("ID");
				String PW=rs.getString("PW");
			//	System.out.println("ID : "+ID+"\nPW : "+PW+"\n---------------------------\n");
				cnt++;
				
			}
			rs.close();
			state.close();
			conn.close();
			
			
			
		}catch(Exception e) {
			System.out.println("처음 오류남");
		}finally {
			try {
				if(state!=null) {
					state.close();
				}
			}catch(SQLException ex1) {
				System.out.println("두번째 오류남");
			}
			try {
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException ex1) {
				System.out.println("세번째");
			}
			
		}
		//System.out.println("MYSQL Close");
	}
	
	public int ckuser() {
		return cnt;
	}
}
