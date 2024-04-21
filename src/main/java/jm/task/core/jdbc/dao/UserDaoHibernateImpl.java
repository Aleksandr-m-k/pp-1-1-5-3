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
    private static final String SQL_COMMAND_GET_USERS = "from User";// указывается не имя таблицы, а имя КЛАССА
    private static final String SQL_COMMAND_CLEAN = "DELETE from Users";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(SQL_COMMAND_CREATE).executeUpdate();
                transaction.commit();
                System.out.println("Таблица создана");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                System.out.println("ggdfgjfgkjh;hjlddlhkkj");
            }
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(SQL_COMMAND_DROP).executeUpdate();
                transaction.commit();
                System.out.println("Таблица удалена");
            } catch (Exception e) {
                System.out.println("таблица не удалена");
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                    System.out.println("пользователь удален");
                }
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                users = session.createQuery(SQL_COMMAND_GET_USERS).getResultList();
                System.out.println("получен список всех пользователей!!!");
                for (User user : users) {
                    System.out.println(user);
                }
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(SQL_COMMAND_CLEAN).executeUpdate();
                transaction.commit();
                System.out.println("таблица очищена");

            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
