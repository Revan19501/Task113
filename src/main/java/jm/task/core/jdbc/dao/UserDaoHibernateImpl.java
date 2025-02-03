package jm.task.core.jdbc.dao;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE users (id BIGINT AUTO_INCREMENT,  " +
            "name VARCHAR(80), lastName VARCHAR(100), " +
            "age TINYINT (100), " +
            "PRIMARY KEY (id));";
    public UserDaoHibernateImpl() {

    }

    Util util = new Util();


    @Transactional
    public void createUsersTable() {
        //прописал создание таблицы в пропертис в классе утилс, поэтому этот метод не очень нужен, думаю
        SessionFactory sf = util.getFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query query = session.createNativeQuery(CREATE_TABLE);
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
    }

    @Transactional
    public void dropUsersTable() {
        SessionFactory sf = util.getFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
        } finally {
            sf.close();
        }

    }

    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sf = util.getFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
    }

    @Transactional
    public void removeUserById(long id) {
        SessionFactory sf = util.getFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            System.out.println("Удаляем прользователя: " + user.getName() + " " + user.getLastName());
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
    }

    @Transactional
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SessionFactory sf = util.getFactory();
        Session session = sf.openSession();

        try {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
        return users;
    }

    @Transactional
    public void cleanUsersTable() {
        SessionFactory sf = util.getFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
    }
}
