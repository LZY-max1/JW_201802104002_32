package dao;

import domain.User;
import service.TeacherService;
import service.UserService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;


public final class UserDao {
	private static UserDao userDao=new UserDao();
	private UserDao(){}
	public static UserDao getInstance(){
		return userDao;
	}


	public void changePassword(User user,String newPassword) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE user SET password = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,newPassword);
		pstmt.setInt(2,user.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}


	public User login(String username,String password) throws SQLException {
		User user = null;
		Connection connection = JdbcHelper.getConn();
		String login_sql = "SELECT * FROM user where username = ? AND password = ?";
		PreparedStatement pstmt = connection.prepareStatement(login_sql);
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()){
			user = UserService.getInstance().find(resultSet.getInt("id"));

		}

//		if (resultSet.next()){
//			user = new User(
//					resultSet.getInt("id"),
//					resultSet.getString("username"),
//					resultSet.getString("password"),
//					resultSet.getDate("password"),
//					TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
//			);
//		}
		return user;
	}


	public Collection<User> findAll() throws SQLException {
		Collection<User> users = new TreeSet<User>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from user");
		while(resultSet.next()){
			User user = new User(
					resultSet.getInt("id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getDate("loginTime"),
					TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
			);

			users.add(user);
		}
		return users;
	}


	public User find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String findUser_sql = "SELECT * FROM user where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(findUser_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		resultSet.next();
		User user = new User(
				resultSet.getInt("id"),
				resultSet.getString("username"),
				resultSet.getString("password"),
				resultSet.getDate("loginTime"),
				TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
		);
		return user;
	}

	public User findByUsername(String username) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String findUser_sql = "SELECT * FROM user WHERE username = ?";
		PreparedStatement pstmt = connection.prepareStatement(findUser_sql);
		pstmt.setString(1,username);
		ResultSet resultSet = pstmt.executeQuery();
		resultSet.next();
		User user = new User(
				resultSet.getInt("id"),
				resultSet.getString("username"),
				resultSet.getString("password"),
				resultSet.getDate("loginTime"),
				TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
		);
		return user;
	}


	public void update(User user) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE user SET username = ?,password = ?,loginTime = ?,teacher_id = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,user.getUsername());
		pstmt.setString(2,user.getPassword());
		pstmt.setDate(3, (Date) user.getLoginTime());
		pstmt.setInt(4,user.getTeacher().getId());
		pstmt.setInt(5,user.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}

	public void add(User user) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addUser_sql = "INSERT INTO user(username,password,loginTime,teacher_id) VALUES" +
				" (?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addUser_sql);

		pstmt.setString(1, user.getUsername());
		pstmt.setString(2,user.getPassword());
		pstmt.setDate(3, (Date) user.getLoginTime());
		pstmt.setInt(4,user.getTeacher().getId());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");

		JdbcHelper.close(pstmt,connection);
	}

	public void add(Connection connection,User user) throws SQLException {
		String addUser_sql = "INSERT INTO user(username,password,loginTime,teacher_id) VALUES" +
				" (?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addUser_sql);

		pstmt.setString(1, user.getUsername());
		pstmt.setString(2,user.getPassword());
		pstmt.setDate(3, (Date) user.getLoginTime());
		pstmt.setInt(4,user.getTeacher().getId());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");

		pstmt.close();
	}

	public void delete(Integer id) throws SQLException {
		User user = this.find(id);
		this.delete(user);
	}

	public void delete(User user) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String deleteUser_sql = "DELETE FROM user WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(deleteUser_sql);
		pstmt.setInt(1,user.getId());
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除了 " + affectedRowNum +" 行记录");

		JdbcHelper.close(pstmt,connection);
	}



	private static void display(Collection<User> users) {
		for (User user : users) {
			System.out.println(user);
		}
	}


}
