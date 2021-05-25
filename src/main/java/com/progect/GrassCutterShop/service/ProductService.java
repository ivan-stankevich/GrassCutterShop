package com.progect.GrassCutterShop.service;

import com.progect.GrassCutterShop.dao.impl.ProductDaoImpl;
import com.progect.GrassCutterShop.entity.Product;
import com.progect.GrassCutterShop.entity.enums.Engine;
import com.progect.GrassCutterShop.entity.enums.Manufacturer;
import com.progect.GrassCutterShop.entity.enums.Type;
import com.progect.GrassCutterShop.exception.DaoException;
import com.progect.GrassCutterShop.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.progect.GrassCutterShop.exception.ExceptionMessage.SERVICE_EXCEPTION_MESSAGE;


@SuppressWarnings("Duplicates")
public class ProductService {
    private static ProductService instance = new ProductService();

    /**
     * Return a instance of {@code DrugService}
     *
     * @return {@code DrugService} instance
     */
    public static ProductService getInstance() {
        return instance;
    }

    private ProductService() {
    }

    /**
     * Find product by his id
     *
     * @param productId its id of the product which need
     *               to find
     * @return {@code Optional} of {@code Product} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public Optional<Product> findProductById(Long productId) throws ServiceException {
        Optional<Product> maybeProduct;
        try {
            maybeProduct = ProductDaoImpl.getInstance().findById(productId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return maybeProduct;
    }

    /**
     * Add new product
     *
     * @param product its a new product which need to add
     * @return {@code Long} id of the new product after saving
     * @throws ServiceException if Dao layer can't do own method
     */
    public void addProduct(Product product) throws ServiceException {
        try {
            ProductDaoImpl.getInstance().saveProduct(product);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find all products
     *
     * @return {@code List} of the all products
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<Product> findAllProducts() throws ServiceException {
        List<Product> productList;
        try {
            productList = ProductDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return productList;
    }

    /**
     * Delete from base product by his id
     *
     * @param productId its id of the product which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteProduct(Long productId) throws ServiceException {
        boolean result = false;
        try {
            if (ProductDaoImpl.getInstance().deleteById(productId)) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * make a new {@code Product} object from data
     *
     * @param title             its a product name
     * @param manufacturer      its a product composition
     * @param type              its a product indications
     * @param engine            its a product mode of application
     * @param price             its a product contraindications
     * @return {@code Product} object build from data
     */
    public Product makeProduct(String title, Manufacturer manufacturer, Type type, Engine engine, BigDecimal price) {
        return buildProduct(title, manufacturer, type, engine, price);
    }

    /**
     * Update product
     *
     * @param product     its a {@code Product} object which data will be updated
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean updateProduct(Product product) throws ServiceException {
        boolean result;
        try {
            result = ProductDaoImpl.getInstance().update(product);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    private Product buildProduct(String title, Manufacturer manufacturer, Type type, Engine engine, BigDecimal price){
        return Product.builder()
                .title(title)
                .manufacturer(manufacturer)
                .type(type)
                .engine(engine)
                .build();
    }
}
