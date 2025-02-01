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
    private static final String UPDATE_USER = "UPDATE users SET age = ? WHERE id = ?;";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String CREATE_TABLE = "CREATE TABLE users (id BIGINT AUTO_INCREMENT,  name VARCHAR(80), lastName  VARCHAR(100), age TINYINT (100), PRIMARY KEY (id));";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Таблица уже есть");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("DROP TABLE users")) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Такой таблицы нет");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            System.out.println("User с именем " + name + " добавлен в базу данных");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "Ошибка в сейв юзерс ");
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "Ошибка в делит юзерс ");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users")) {
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
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE from users")) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
