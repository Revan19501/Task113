package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String dbUrl = "jdbc:mysql://localhost:3306/mysql";
    private static String dbUsername = "root";
    private static String dbPassword = "1234";


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
