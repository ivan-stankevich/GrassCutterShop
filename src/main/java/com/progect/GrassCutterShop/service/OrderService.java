package com.progect.GrassCutterShop.service;


import com.progect.GrassCutterShop.dao.impl.OrderDaoImpl;
import com.progect.GrassCutterShop.entity.Order;
import com.progect.GrassCutterShop.exception.DaoException;
import com.progect.GrassCutterShop.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.progect.GrassCutterShop.exception.ExceptionMessage.SERVICE_EXCEPTION_MESSAGE;


public class OrderService {
    private static OrderService instance = new OrderService();
    /**
     * return the instance of the {@code OrderService}
     *
     * @return instance of the {@code OrderService}
     */
    public static OrderService getInstance() {
        return instance;
    }

    private OrderService() {
    }

    /**
     * Save into base user's order
     *
     * @param order its a {@code Order} object which
     *              need to save
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean saveOrder(Order order) throws ServiceException {
        boolean result;
        try {
            result = OrderDaoImpl.getInstance().saveOrder(order);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Return {@code List} of the user's order
     *
     * @param userId its id of the user which orders need to find
     * @return {@code List} of the user's order
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Order> getOrderListByUserId(Long userId) throws ServiceException {
        List<Order> orderList;
        try {
            orderList = OrderDaoImpl.getInstance().findAllByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return orderList;
    }

    /**
     * Return {@code List} of the user's order
     *
     * @param userId its id of the user which orders need to find
     * @return {@code List} of the user's order
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orderList;
        try {
            orderList = OrderDaoImpl.getInstance().findAllOrders();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return orderList;
    }

    /**
     * Delete from database user's order
     *
     * @param orderId its order id which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteOrder(Long orderId) throws ServiceException, DaoException {
        boolean result;
        result = OrderDaoImpl.getInstance().delete(orderId);
        return result;
    }


    /**
     * Create {@code Order} object from data
     *
     * @param userId        its a buyer id
     * @param productId     its a product which buyer need
     * @param productTitle  its a products title which buyer need
     * @param orderTime     its a order's time
     * @param orderCost     its a order's cost
     * @return {@code Order} object created from data
     */
    public Order makeOrder(Long userId, Long productId, String productTitle, LocalDateTime orderTime, BigDecimal orderCost) {
        return buildOrder(userId, productId, productTitle, orderTime, orderCost);
    }

    private Order buildOrder(Long userId, Long productId, String productTitle, LocalDateTime orderTime, BigDecimal orderCost) {
        return Order.builder()
                .userId(userId)
                .productId(productId)
                .productTitle(productTitle)
                .orderTime(orderTime)
                .orderCost(orderCost)
                .build();
    }
}
