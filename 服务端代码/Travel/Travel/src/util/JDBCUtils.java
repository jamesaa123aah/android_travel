package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCUtils {
       static Connection conn=null;
       public static Connection getConnection() throws Exception{
    	   Class.forName("com.mysql.cj.jdbc.Driver");
    	   String url="jdbc:mysql://localhost:3306/travel?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC";
    	   conn=(Connection) DriverManager.getConnection(url,"root","123456");
		return conn;   
       }
       public static void close(Connection conn,PreparedStatement ps,ResultSet rs) {
    	   if(rs!=null) {
    		   try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }
    	   if(ps!=null) {
    		   try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }
    	   if(conn!=null) {
    		   try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }
       }
}
