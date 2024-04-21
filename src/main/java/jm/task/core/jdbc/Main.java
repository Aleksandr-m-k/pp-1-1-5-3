package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        Util util = new Util();
        util.buildSessionFactory();
        UserDaoHibernateImpl userDaoHibernate=new UserDaoHibernateImpl();
       userDaoHibernate.createUsersTable();
       userDaoHibernate.saveUser("alex","smith",(byte) 38);
       userDaoHibernate.saveUser("alex","smith",(byte) 38);
       userDaoHibernate.getAllUsers();
       userDaoHibernate.removeUserById(2l);
       userDaoHibernate.cleanUsersTable();
       userDaoHibernate.dropUsersTable();


     UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Name1", "Smith", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

        System.out.println(userDao.getAllUsers());
        userDao.removeUserById(1l);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        System.out.println(userDao.getAllUsers());
        userDao.dropUsersTable();
    }
}
