package multichat_1;
import java.sql.*;


public class LOGIN {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //����̹�
	private final String DB_URL = "jdbc:mysql://localhost:3306/talkdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //������ DB ����
		
	private final String USER_NAME = "root"; //DB�� ������ ����� �̸��� ����� ����
	private final String PASSWORD = "anji314"; //������� ��й�ȣ�� ����� ����
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
			//System.out.println(userid+"�̰ɷ� �α���\n");
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
			System.out.println("ó�� ������");
		}finally {
			try {
				if(state!=null) {
					state.close();
				}
			}catch(SQLException ex1) {
				System.out.println("�ι�° ������");
			}
			try {
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException ex1) {
				System.out.println("����°");
			}
			
		}
		//System.out.println("MYSQL Close");
	}
	
	public int ckuser() {
		return cnt;
	}
}
