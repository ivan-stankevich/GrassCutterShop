package com.progect.GrassCutterShop.dao;


import com.progect.GrassCutterShop.entity.Order;
import com.progect.GrassCutterShop.exception.DaoException;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    /**
     * Save in database new user's {@code Order}
     *
     * @param order is the {@code Order} which need to save
     * @return {@code true} if success or {@code false} if failed
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    boolean saveOrder(Order order) throws DaoException;

    boolean delete(Long entityId) throws DaoException;

    List<Order> findAllOrders() throws DaoException;

    List<Order> findAllByUserId(Long userId) throws DaoException;
}
