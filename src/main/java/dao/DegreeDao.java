package dao;


import domain.Degree;
import service.DegreeService;
import util.JdbcHelper;
import util.ShowTable;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class DegreeDao {
	private static DegreeDao degreeDao=
			new DegreeDao();
	private DegreeDao(){}
	public static DegreeDao getInstance() throws SQLException {

		return degreeDao;

	}


	public Collection<Degree> findAll() throws SQLException {
		Collection<Degree> degrees = new TreeSet<Degree>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from degree");
		while(resultSet.next()){
			System.out.print("id = " + resultSet.getInt("id"));
			System.out.print(",");
			System.out.print("no = " + resultSet.getString("no"));
			System.out.print(",");
			System.out.print("description = " + resultSet.getString("description"));
			System.out.print(",");
			System.out.print("remarks = " + resultSet.getString("remarks"));
			System.out.println(".");
			Degree degree = new Degree(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
			degrees.add(degree);
		}

		return degrees;
	}

	public Degree find(Integer id) throws SQLException {

		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM degree where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		Degree degree = null;
		while (resultSet.next()) {
			degree = new Degree(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"));
		}
		return degree;


	}

	public void update(Degree degree) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE degree SET description = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,degree.getDescription());
		pstmt.setInt(2,degree.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}

	public void add(Degree degree) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO degree(no,description,remarks) VALUES" +
				" (?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setString(1, degree.getNo());
		pstmt.setString(2,degree.getDescription());
		pstmt.setString(3,degree.getRemarks());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}
	public void delete(Degree degree) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "DELETE FROM degree WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,degree.getId());
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}

	public void delete(Integer id) throws SQLException {
		Degree degree = this.find(id);
		this.delete(degree);
	}

	public static void main(String[] args) throws SQLException {
		Degree degreeToAdd = DegreeService.getInstance().find(1);
		System.out.println(degreeToAdd.getDescription());
		degreeToAdd.setDescription("b");
		System.out.println(degreeToAdd.getDescription());
		degreeDao.update(degreeToAdd);
		Degree degree1 = DegreeService.getInstance().find(1);

		System.out.println("Description = " + degree1.getDescription());
	}


}

