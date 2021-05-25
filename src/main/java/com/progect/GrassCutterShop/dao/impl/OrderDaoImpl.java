package com.progect.GrassCutterShop.dao.impl;

import com.progect.GrassCutterShop.connection.ConnectionPool;
import com.progect.GrassCutterShop.connection.ProxyConnection;
import com.progect.GrassCutterShop.dao.OrderDao;
import com.progect.GrassCutterShop.entity.Entity;
import com.progect.GrassCutterShop.entity.Order;
import com.progect.GrassCutterShop.exception.ConnectionPoolException;
import com.progect.GrassCutterShop.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.progect.GrassCutterShop.exception.ExceptionMessage.CONNECTION_POOL_EXCEPTION_MESSAGE;
import static com.progect.GrassCutterShop.exception.ExceptionMessage.DAO_EXCEPTION_MESSAGE;


public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl ourInstance = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return ourInstance;
    }

    private OrderDaoImpl() {
    }

    private static final String SQL_SAVE_ORDER = "INSERT INTO shop.user_order (user_id, product_id, product_title, order_time, order_cost) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM shop.user_order";
    private static final String SQL_FIND_ALL_ORDERS_BY_USER_ID = SQL_FIND_ALL + " WHERE user_id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM shop.user_order WHERE order_id = ?";

    @Override
    public boolean saveOrder(Order order) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setString(3, order.getProductTitle());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getOrderTime()));
            preparedStatement.setBigDecimal(5, order.getOrderCost());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                result = true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Long entityId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entityId);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<Order> findAllOrders() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return orderList;
    }

    @Override
    public List<Order> findAllByUserId(Long userId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return orderList;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .orderId(resultSet.getLong("order_id"))
                .userId(resultSet.getLong("user_id"))
                .productId(resultSet.getLong("product_id"))
                .productTitle(resultSet.getString("product_title"))
                .orderTime(resultSet.getTimestamp("order_time").toLocalDateTime())
                .orderCost(resultSet.getBigDecimal("order_cost"))
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
