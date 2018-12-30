package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import util.JDBCUtils;

public class UserDaoIple implements UserDao{
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> list=null;
		try {
			conn=JDBCUtils.getConnection();
			String sql="select * from user";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<User>();
			while(rs.next()) {
				User user=new User();
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			JDBCUtils.close(conn,ps,rs);
		}
		return list;
	}

	@Override
	public void insertElement(User people) {
		// TODO Auto-generated method stub
		try {
			conn=JDBCUtils.getConnection();
			String sql="INSERT INTO user(name,password) VALUES(?,?);";
			ps=conn.prepareStatement(sql);
			ps.setString(1,people.getName());
			ps.setString(2,people.getPassword());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			JDBCUtils.close(conn,ps,rs);
		}
		
	}

}
