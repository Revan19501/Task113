package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



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

    public SessionFactory getFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mysql");
        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "1234");
        properties.setProperty("hibernate.show_sql", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        Configuration configuration = new Configuration().addAnnotatedClass(User.class);

        SessionFactory factory = configuration.setProperties(properties).
                buildSessionFactory();
        return factory;
    }

}
