package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowTable {
    public static void showTable(PreparedStatement pstmt) throws SQLException {
        ResultSet resultSet = pstmt.executeQuery("select * from degree");
        while(resultSet.next()){
            System.out.print("id = " + resultSet.getInt("id"));
            System.out.print(",");
            System.out.print("no = " + resultSet.getString("no"));
            System.out.print(",");
            System.out.print("description = " + resultSet.getString("description"));
            System.out.print(",");
            System.out.print("remarks = " + resultSet.getString("remarks"));
            System.out.println(".");
        }
    }
}
