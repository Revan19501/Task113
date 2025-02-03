package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String INSERT_USER = "INSERT INTO users(name, lastName , age) VALUES (?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users  (id BIGINT AUTO_INCREMENT,  name VARCHAR(80), lastName  VARCHAR(100), age TINYINT (100), PRIMARY KEY (id));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE users;";
    private static final Connection conn = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement ps = conn.prepareStatement(CREATE_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Таблица уже есть");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement ps = conn.prepareStatement(DROP_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Такой таблицы нет");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "Ошибка в сейв юзерс ");
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement ps = conn.prepareStatement(DELETE_USER)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "Ошибка в делит юзерс ");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName  = rs.getString("lastName");
                byte age = rs.getByte("age");
                users.add(new User(id, name, lastName , age));
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "Ошибка тут");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement ps = conn.prepareStatement(CLEAN_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
