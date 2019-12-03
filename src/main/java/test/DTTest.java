package test;

import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DTTest {
    public static void main(String[] args) {
        //声明连接对象和预编译对象
        Connection connection =null;
        PreparedStatement ps = null;
        try {
            connection = JdbcHelper.getConn();
            //事物开始
            connection.setAutoCommit(false);
            ps = connection.prepareStatement("INSERT INTO school(description,no) VALUES (?,?)");
            ps.setString(1,"建筑学院");
            ps.setString(2,"22");
            //执行第一条语句
            ps.executeUpdate();
            ps = connection.prepareStatement("INSERT INTO school(description,no) VALUES (?,?)");
            ps.setString(1,"艺术学院");
            ps.setString(2,"02");
            //执行第二条语句,违反no的唯一约束性
            ps.executeUpdate();
            //提交当前操作
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
            try {
                if (connection != null){
                    //回滚当前操作
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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
            JdbcHelper.close(ps,connection);
        }

    }
}
