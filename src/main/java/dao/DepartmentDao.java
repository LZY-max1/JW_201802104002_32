package dao;


import domain.Department;
import service.DepartmentService;
import service.SchoolService;
import util.JdbcHelper;
import util.ShowTable;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class DepartmentDao {
	private static Collection<Department> departments;
	static {

		departments = new TreeSet<Department>();
	}

	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}

	public Collection<Department>  findAllBySchool(Integer school_id) throws SQLException{
        //创建集合departments
		Collection<Department> departments = new TreeSet<Department>();
        //获得连接对象
		Connection connection = JdbcHelper.getConn();
        //在该连接上进行预编译
		String updateDegree_sql = "SELECT * FROM department where school_id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
        //给参数赋值
		pstmt.setInt(1,school_id);
        //执行SQL查询语句并获得结果集
		ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
		while(resultSet.next()) {
			Department department = new Department(resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolDao.getInstance().find(resultSet.getInt("school_id")));
			departments.add(department);
		}
		JdbcHelper.close(resultSet,pstmt,connection);
		return departments;

	}
	public Collection<Department> findAll() throws SQLException {
		Collection<Department> departments = new TreeSet<Department>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from department");
		while(resultSet.next()){
			System.out.print("id = " + resultSet.getInt("id"));
			System.out.print(",");
			System.out.print("no = " + resultSet.getString("no"));
			System.out.print(",");
			System.out.print("description = " + resultSet.getString("description"));
			System.out.print(",");
			System.out.print("remarks = " + resultSet.getString("remarks"));
			System.out.print(",");
			System.out.print("school_id = " + resultSet.getString("school_id"));
			System.out.println(".");
			Department department = new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id")));
			departments.add(department);
		}
		return departments;
	}

	public Department find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "SELECT * FROM department where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		Department department = null;
        while(resultSet.next()) {
            department = new Department(resultSet.getInt("id"),
                    resultSet.getString("description"),
                    resultSet.getString("no"),
                    resultSet.getString("remarks"),
                    SchoolDao.getInstance().find(resultSet.getInt("school_id")));
        }
		return department;
	}



	public void update(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDegree_sql = "UPDATE department SET description = ? where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
		pstmt.setString(1,department.getDescription());
		pstmt.setInt(2,department.getId());
		pstmt.executeUpdate();
		JdbcHelper.close(pstmt,connection);
	}

	/*public void add(Department department) throws SQLException {
		departments.add(department);
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO department(description,no,remarks,school_id) VALUES" +
				" (?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setString(1, department.getDescription());
		pstmt.setString(2,department.getNo());
		pstmt.setString(3,department.getRemarks());
		pstmt.setInt(4,department.getSchool().getId());

		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}
*/
    public boolean add(Department department) throws SQLException,ClassNotFoundException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String addDepartment_sql = "INSERT INTO department (description,no,remarks,school_id) VALUES"+" (?,?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addDepartment_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,department.getDescription());
        preparedStatement.setString(2,department.getNo());
        preparedStatement.setString(3,department.getRemarks());
        preparedStatement.setInt(4,department.getSchool().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }
	public void delete(Integer id) throws SQLException {
		Department department = this.find(id);
		this.delete(department);
	}

	public void delete(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "DELETE FROM department WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		pstmt.setInt(1,department.getId());
		int affectedRowNum = pstmt.executeUpdate();
		departments.remove(department);
		System.out.println("删除了 " + affectedRowNum +" 行记录");
		ShowTable.showTable(pstmt);
		JdbcHelper.close(pstmt,connection);
	}

	public static void main(String[] args) throws SQLException {
		Department departmentToChange = DepartmentService.getInstance().find(1);
		departmentToChange.setDescription("管理");
		departmentDao.update(departmentToChange);
		Department departmentChanged = DepartmentService.getInstance().find(1);
		System.out.println(departmentChanged.getDescription());

	}
}

