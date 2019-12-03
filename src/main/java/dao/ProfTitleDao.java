package dao;


import domain.ProfTitle;
import util.JdbcHelper;
import util.ShowTable;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class ProfTitleDao {
	private static ProfTitleDao profTitleDao=new ProfTitleDao();
	private ProfTitleDao(){}
	public static ProfTitleDao getInstance(){
		return profTitleDao;
	}
	private static Collection<ProfTitle> profTitles;
	static {

		profTitles = new TreeSet<ProfTitle>();
	}

	public Collection<ProfTitle> findAll() throws SQLException {
		Collection<ProfTitle> profTitles = new TreeSet<ProfTitle>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from proftitle");
		while(resultSet.next()){
			System.out.print("id = " + resultSet.getInt("id"));
			System.out.print(",");
			System.out.print("description = " + resultSet.getString("description"));
			System.out.print(",");
			System.out.print("no = " + resultSet.getString("no"));
			System.out.print(",");
			System.out.print("remarks = " + resultSet.getString("remarks"));
			System.out.println(".");
			ProfTitle profTitle = new ProfTitle(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"));
			profTitles.add(profTitle);
		}
		return profTitles;
	}

	public ProfTitle find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM proftitle where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		ProfTitle profTitle = null;
		while (resultSet.next()) {
			profTitle = new ProfTitle(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks")
			);
		}
		return profTitle;
	}

	public void update(ProfTitle profTitle) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE proftitle SET description = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,profTitle.getDescription());
		pstmt.setInt(2,profTitle.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}

	public void add(ProfTitle profTitle) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addProfTitle_sql = "INSERT INTO profTitle(no,description,remarks) VALUES" +
				" (?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addProfTitle_sql);
		pstmt.setString(1, profTitle.getNo());
		pstmt.setString(2,profTitle.getDescription());
		pstmt.setString(3,profTitle.getRemarks());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}

	public void delete(Integer id) throws SQLException {
		ProfTitle profTitle = this.find(id);
		this.delete(profTitle);
	}

	public void delete(ProfTitle profTitle) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "DELETE FROM proftitle WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,profTitle.getId());
		int affectedRowNum = pstmt.executeUpdate();
		profTitles.remove(profTitle);
		System.out.println("删除了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}
}

