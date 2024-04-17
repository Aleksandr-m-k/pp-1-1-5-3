package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.sessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final String SQL_COMMAND_CREATE = "CREATE TABLE `Users` ( id int NOT NULL AUTO_INCREMENT, name varchar(45) NOT NULL," +
            " lastName varchar(45) DEFAULT NULL, age int NOT NULL, PRIMARY KEY (id))";
    private static final String SQL_COMMAND_DROP = "drop table Users";
    private static final String SQL_COMMAND_GET_USERS = "select * from users";
    private static final String SQL_COMMAND_CLEAN = "DELETE from Users";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(SQL_COMMAND_CREATE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(SQL_COMMAND_DROP).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("пользователь удален");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createQuery(SQL_COMMAND_GET_USERS).getResultList();// указывается не имя таблицы, а имя КЛАССА
            System.out.println("получен список всех пользователей!!!");
            for (User user : users) {
                System.out.println(user);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при формировании списка");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(SQL_COMMAND_CLEAN).executeUpdate();
            transaction.commit();
            System.out.println("таблица очищена");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("не вышло");
        }
    }
}
