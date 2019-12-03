package dao;


import domain.School;
import util.JdbcHelper;
import util.ShowTable;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class SchoolDao {
	private static SchoolDao schoolDao = new SchoolDao();
	private static Collection<School> schools;
	static{
		schools = new TreeSet<School>();
	}
	
	public SchoolDao(){}
	
	public static SchoolDao getInstance(){
		return schoolDao;
	}

	public School addWithSP(School school) throws SQLException {
		Connection connection =  JdbcHelper.getConn();
		CallableStatement callableStatement = connection.prepareCall("{CALL sp_addSchool(?,?,?,?)}");
		callableStatement.registerOutParameter(4, Types.BIGINT);
		callableStatement.setString(1,school.getDescription());
		callableStatement.setString(2,school.getNo());
		callableStatement.setString(3,school.getRemarks());
		callableStatement.execute();
		int id = callableStatement.getInt(4);
		school.setId(id);
		callableStatement.close();
		connection.close();
		return school;
	}

	public Collection<School> findAll() throws SQLException {
		Collection<School> schools = new TreeSet<School>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from school");
		while(resultSet.next()){
			System.out.print("id = " + resultSet.getInt("id"));
			System.out.print(",");
			System.out.print("no = " + resultSet.getString("no"));
			System.out.print(",");
			System.out.print("description = " + resultSet.getString("description"));
			System.out.print(",");
			System.out.print("remarks = " + resultSet.getString("remarks"));
			System.out.println(".");
			School school = new School(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
			schools.add(school);
		}

		return schools;
	}
	
	public School find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM school where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		School school=null;
		if(resultSet.next()) {
			school = new School(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks")
			);
		}
		JdbcHelper.close(pstmt,connection);
		return school;
	}
	
	public void update(School school) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE school SET description = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,school.getDescription());
		pstmt.setInt(2,school.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}
	
	public void add(School school) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO school(id,no,description,remarks) VALUES" +
				" (?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,school.getId());
		pstmt.setString(2, school.getNo());
		pstmt.setString(3,school.getDescription());
		pstmt.setString(4,school.getRemarks());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
		schools.add(school);

	}

	public void delete(Integer id) throws SQLException {
		School school =this.find(id);
		this.delete(school);
	}
	
	public void delete(School school) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addSchool_sql = "DELETE FROM school WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(addSchool_sql);
		pstmt.setInt(1,school.getId());
		int affectedRowNum = pstmt.executeUpdate();
		schools.remove(school);
		System.out.println("删除了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}
}
