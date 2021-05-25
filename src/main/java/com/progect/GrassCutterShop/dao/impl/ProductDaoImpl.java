package com.progect.GrassCutterShop.dao.impl;

import com.progect.GrassCutterShop.connection.ConnectionPool;
import com.progect.GrassCutterShop.connection.ProxyConnection;
import com.progect.GrassCutterShop.dao.ProductDao;
import com.progect.GrassCutterShop.entity.Product;
import com.progect.GrassCutterShop.entity.enums.Engine;
import com.progect.GrassCutterShop.entity.enums.Manufacturer;
import com.progect.GrassCutterShop.entity.enums.Type;
import com.progect.GrassCutterShop.exception.ConnectionPoolException;
import com.progect.GrassCutterShop.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.progect.GrassCutterShop.exception.ExceptionMessage.*;


@SuppressWarnings("Duplicates")
public class ProductDaoImpl implements ProductDao {
    private static ProductDaoImpl instanse = new ProductDaoImpl();
    public static ProductDaoImpl getInstance() {
        return instanse;
    }

    private static final String SQL_DELETE_PRODUCT = "DELETE FROM shop.product WHERE product_id = ?";
    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT * FROM shop.product ";
    private static final String SQL_FIND_PRODUCT_BY_ID = SQL_FIND_ALL_PRODUCTS + "WHERE product_id = ?";
    private static final String SQL_SAVE_PRODUCT = "INSERT INTO shop.product (title, manufacturer, type, engine, price) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE shop.product SET title = ?, " +
            "manufacturer = ?, type = ?, engine = ?, price = ? WHERE product_id = ?";

    @Override
    public boolean deleteById(Long productId) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement productPreparedStatemen = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            productPreparedStatemen = connection.prepareStatement(SQL_DELETE_PRODUCT);
            productPreparedStatemen.setLong(1, productId);
            productPreparedStatemen.executeUpdate();

            connection.commit();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DaoException(TRANSACTION_EXCEPTION_MESSAGE, e1);
            }
        } finally {
            closeStatement(productPreparedStatemen);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<Product> findAll() throws DaoException {
        List<Product> productList = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_PRODUCTS);

            while (resultSet.next()) {
                Product product = buildProduct(resultSet);
                productList.add(product);
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return productList;
    }

    public Optional<Product> findById(Long id) throws DaoException {
        Optional <Product> product = Optional.empty();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                product = Optional.of(buildProduct(resultSet));
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return product;
    }

    @Override
    public boolean update(Product product) throws DaoException {
        boolean result = false;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT);

            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setInt(2, product.getManufacturer().ordinal());
            preparedStatement.setInt(3, product.getType().ordinal());
            preparedStatement.setInt(4, product.getEngine().ordinal());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.setLong(6, product.getProductId());
            preparedStatement.executeUpdate();
            result = true;

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public void saveProduct(Product product) throws DaoException {
        Long productId = null;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SAVE_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setInt(2, product.getManufacturer().ordinal());
            preparedStatement.setInt(3, product.getType().ordinal());
            preparedStatement.setInt(4, product.getEngine().ordinal());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(CONNECTION_POOL_EXCEPTION_MESSAGE, e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .productId(resultSet.getLong("product_id"))
                .title(resultSet.getString("title"))
                .manufacturer(Manufacturer.values()[resultSet.getInt("manufacturer")])
                .type(Type.values()[resultSet.getInt("type")])
                .engine(Engine.values()[resultSet.getInt("engine")])
                .price(resultSet.getBigDecimal("price"))
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


