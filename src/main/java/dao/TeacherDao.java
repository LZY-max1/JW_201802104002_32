package dao;


import domain.Teacher;
import domain.User;
import service.DegreeService;
import service.DepartmentService;
import service.ProfTitleService;
import service.UserService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class TeacherDao {
	private static TeacherDao teacherDao=new TeacherDao();
	private TeacherDao(){}
	public static TeacherDao getInstance(){
		return teacherDao;
	}

	public Collection<Teacher> findAll() throws SQLException {
		Collection<Teacher> teachers = new TreeSet<Teacher>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from teacher");
		while(resultSet.next()){
			System.out.print("id = " + resultSet.getInt("id"));
			System.out.print(",");
			System.out.print("name = " + resultSet.getString("name"));
			System.out.print(",");
			System.out.print("no = " + resultSet.getString("no"));
			System.out.print(",");
			System.out.print("proftitle_id = " + resultSet.getString("proftitle_id"));
			System.out.print(",");
			System.out.print("degree_id = " + resultSet.getString("degree_id"));
			System.out.print(",");
			System.out.print("department_id = " + resultSet.getString("department_id"));
			System.out.println(".");
			Teacher teacher = new Teacher(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					resultSet.getString("no"),
					ProfTitleService.getInstance().find(resultSet.getInt("proftitle_id")),
					DegreeService.getInstance().find(resultSet.getInt("degree_id")),
					DepartmentService.getInstance().find(resultSet.getInt("department_id")));
			teachers.add(teacher);
		}
		return teachers;
	}
	
	public Teacher find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM teacher where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		Teacher teacher = null;
		while (resultSet.next()) {
			teacher = new Teacher(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					resultSet.getString("no"),
					ProfTitleService.getInstance().find(resultSet.getInt("proftitle_id")),
					DegreeService.getInstance().find(resultSet.getInt("degree_id")),
					DepartmentService.getInstance().find(resultSet.getInt("department_id"))
			);
		}
		return teacher;
	}

	public void update(Teacher teacher) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE teacher SET name=?,no = ?,proftitle_id = ?,degree_id = ?,department_id=? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,teacher.getName());
		pstmt.setString(2,teacher.getNo());
		pstmt.setInt(3,teacher.getTitle().getId());
		pstmt.setInt(4,teacher.getDegree().getId());
		pstmt.setInt(5,teacher.getDepartment().getId());

		pstmt.setInt(6,teacher.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}
	
	public void add(Teacher teacher) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = JdbcHelper.getConn();
			connection.setAutoCommit(false);
			String addTeacher_sql = "INSERT INTO teacher(name,no,proftitle_id,degree_id,department_id) VALUES" +
					" (?,?,?,?,?)";
			pstmt = connection.prepareStatement(addTeacher_sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, teacher.getName());
			pstmt.setString(2, teacher.getNo());
			pstmt.setInt(3, teacher.getTitle().getId());
			pstmt.setInt(4, teacher.getDegree().getId());
			pstmt.setInt(5, teacher.getDepartment().getId());

			int affectedRowNum = pstmt.executeUpdate();
			System.out.println("添加了 " + affectedRowNum + " 行记录");

			ResultSet resultSet = pstmt.getGeneratedKeys();
			resultSet.next();
			int teacherId = resultSet.getInt(1);
			teacher.setId(teacherId);

			java.util.Date date_util= new java.util.Date();
			Long date_long = date_util.getTime();
			Date date_sql = new Date(date_long);

			User user = new User(
					teacher.getNo(),
					teacher.getNo(),
					date_sql,
					teacher
			);
			UserService.getInstance().add(connection, user);
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (connection != null) {
					//恢复自动提交
					connection.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//ShowTable.showTable(pstmt);
			JdbcHelper.close(pstmt, connection);
		}
	}
	public void delete(Integer id) throws SQLException {
		Teacher teacher = this.find(id);
		this.delete(teacher);
	}

	public void delete(Teacher teacher) throws SQLException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {

			connection = JdbcHelper.getConn();
			String deleteUser_sql = "DELETE FROM user WHERE teacher_id = ?";
			pstmt = connection.prepareStatement(deleteUser_sql);
			pstmt.setInt(1,teacher.getId());
			int affectedRowNum = pstmt.executeUpdate();
			System.out.println("删除了 " + affectedRowNum +" 行记录");

			String deleteTeacher_sql = "DELETE FROM teacher WHERE id = ?";
			pstmt = connection.prepareStatement(deleteTeacher_sql);
			pstmt.setInt(1,teacher.getId());
			int affectedRowNum1 = pstmt.executeUpdate();
			System.out.println("删除了 " + affectedRowNum1 +" 行记录");


		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
			try {
				if (connection != null){
					connection.rollback();
				}
			} catch (SQLException e1){
				e.printStackTrace();
			}

		} finally {
			try {
				if (connection != null){
					//恢复自动提交
					connection.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//关闭资源
			JdbcHelper.close(pstmt,connection);
		}
	}

}

