package jm.task.core.jdbc.dao;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Util util = new Util();
    SessionFactory sf = util.getFactory();

    public UserDaoHibernateImpl() {

    }

    public void createUsersTable() {
        Transaction transaction = sf.openSession().getTransaction();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT,  " +
                    "name VARCHAR(80), lastName VARCHAR(100), " +
                    "age TINYINT (100), " +
                    "PRIMARY KEY (id));")
                    .executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            transaction.rollback();
        }
    }


    public void dropUsersTable() {
        Transaction transaction = sf.openSession().getTransaction();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            transaction.rollback();
        }

    }


    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = sf.openSession().getTransaction();
        try (Session session = sf.openSession()){
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }catch (Exception e) {
            transaction.rollback();
        }
    }

    public void removeUserById(long id) {
        Transaction transaction = sf.openSession().getTransaction();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            System.out.println("Удаляем прользователя: " + user.getName() + " " + user.getLastName());
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e) {
            transaction.rollback();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = sf.openSession().getTransaction();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Такой таблицы нет");
        }
        return users;
    }

    public void cleanUsersTable() {
        Transaction transaction = sf.openSession().getTransaction();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            transaction.rollback();
        }
    }
}
