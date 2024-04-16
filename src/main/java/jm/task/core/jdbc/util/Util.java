package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Master1985%#";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        } catch (SQLException e) {
            System.out.println("Соединение не устаеновлено!");
        }
        return connection;
    }

    public static SessionFactory sessionFactory;

    public static SessionFactory buildSessionFactory() {
        try {
            sessionFactory = new Configuration()
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", DB_URL)
                    .setProperty("hibernate.connection.username", DB_USERNAME)
                    .setProperty("hibernate.connection.password", DB_PASSWORD)
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .addAnnotatedClass(User.class).buildSessionFactory();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void sessionFactoryClose() {
        sessionFactory.close();
    }
}
