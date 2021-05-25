package com.progect.GrassCutterShop.dao.impl;

import com.progect.GrassCutterShop.connection.ConnectionPool;
import com.progect.GrassCutterShop.connection.ProxyConnection;
import com.progect.GrassCutterShop.dao.UserDao;
import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.UserRole;
import com.progect.GrassCutterShop.exception.ConnectionPoolException;
import com.progect.GrassCutterShop.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.progect.GrassCutterShop.exception.ExceptionMessage.CONNECTION_POOL_EXCEPTION_MESSAGE;
import static com.progect.GrassCutterShop.exception.ExceptionMessage.DAO_EXCEPTION_MESSAGE;


public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return instance;
    }

    private UserDaoImpl() {
    }
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM shop.users";
    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = SQL_FIND_ALL_USERS + " WHERE login = ? AND password = ?";
    private static final String SQL_FIND_USER_BY_ID = SQL_FIND_ALL_USERS + " WHERE user_id = ?";
    private static final String SQL_SAVE_USER = "INSERT INTO shop.users (first_name, last_name, login, password, role)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM shop.users WHERE user_id = ?";
    private static final String SQL_UPDATE_USER = "UPDATE shop.users SET first_name = ?, last_name = ?, login = ?, password = ?, role = ? WHERE user_id = ?";

    public User findUserByUserId(Long userId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserDTO(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        }finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return user;
    }

    public List<User> findAllUsers() throws DaoException {
        ProxyConnection connection = null;
        Statement statement = null;
        List<User> userList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = buildUserDTO(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return userList;
    }

    public User findUserByLoginAndPassword(String login, String password) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = buildUserDTO(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        }finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return user;
    }

    public User saveUser(User newUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newUser.getFirstName());
            preparedStatement.setString(2, newUser.getLastName());
            preparedStatement.setString(3, newUser.getLogin());
            preparedStatement.setString(4, newUser.getPassword());
            preparedStatement.setInt(5, newUser.getRole().ordinal());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newUser.setUserId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return newUser;
    }

    public boolean deleteUser(Long userId) throws SQLException {
        boolean rowDelete = false;
        try (ProxyConnection connection  = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            preparedStatement.setLong(1, userId);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        return rowDelete;
    }

    public void updateUser(User user) throws SQLException {
        try(ProxyConnection connection  = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setInt(5,user.getRole().ordinal());
            preparedStatement.setLong(6,user.getUserId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    private User buildUserDTO(User user, ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getLong("user_id"))
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    private User buildUserDTO(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getLong("user_id"))
                .login(resultSet.getString("login"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .password(resultSet.getString("password"))
                .role(UserRole.values()[(resultSet.getInt("role"))])
                .build();
    }

    private void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
        }
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
        }
    }

}
