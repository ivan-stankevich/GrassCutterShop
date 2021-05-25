package com.progect.GrassCutterShop.dao;


import com.progect.GrassCutterShop.entity.Product;
import com.progect.GrassCutterShop.exception.DaoException;

import java.util.List;

public interface ProductDao extends BaseDao<Product> {
    boolean deleteById(Long productId) throws DaoException;

    List<Product> findAll() throws DaoException;

    boolean update(Product product) throws DaoException;

    /**
     * Save new drug in database
     *
     * @param product is a {@code Drug} which need to add in database
     * @throws DaoException if have {@code ConnectionPool} error or
     *                      any database error
     */
    void saveProduct(Product product) throws DaoException;
}
