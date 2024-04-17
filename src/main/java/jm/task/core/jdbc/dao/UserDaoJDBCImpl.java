package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String SQL_COMMAND_CREATE = "CREATE TABLE `users` ( id int NOT NULL AUTO_INCREMENT," +
            " name varchar(45) NOT NULL, lastName varchar(45) DEFAULT NULL, age int NOT NULL, PRIMARY KEY (id))";
    private static final String SQL_COMMAND_DROP = "drop table Users";
    private static final String SQL_COMMAND_REMOVE = "DELETE from Users where ID=?";
    private static final String SQL_COMMAND_SAVE = "insert into users (name, lastName, age) values (?,?,?)";
    private static final String SQL_COMMAND_GET_USERS = "select * from users";
    private static final String SQL_COMMAND_CLEAN = "DELETE from Users";
    private static final Connection CONNECTION = Util.getConnection();

    public UserDaoJDBCImpl() {

    }
    // создание таблицы
    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(SQL_COMMAND_CREATE);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица уже была создана");
        }
    }

    // удаление таблицы
    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(SQL_COMMAND_DROP);
            System.out.println("таблица удалена");
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }

    // добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SQL_COMMAND_SAVE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("введено неверное значение");
        }
    }

    // удаление User из таблицы по Id
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SQL_COMMAND_REMOVE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }

    // получение всех User из таблицы
    public List<User> getAllUsers() {
        List<User> userArrayList = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_COMMAND_GET_USERS);
            while (resultSet.next()) {
                User users = new User();
                users.setId(resultSet.getLong("id"));
                users.setName(resultSet.getString("name"));
                users.setLastName(resultSet.getString("lastName"));
                users.setAge(resultSet.getByte("age"));
                userArrayList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    // очистка содержания таблицы
    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SQL_COMMAND_CLEAN)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }
}