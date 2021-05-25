package com.progect.GrassCutterShop.controllers.user;

import com.progect.GrassCutterShop.entity.Order;
import com.progect.GrassCutterShop.entity.Product;
import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.UserRole;
import com.progect.GrassCutterShop.exception.DaoException;
import com.progect.GrassCutterShop.exception.ServiceException;
import com.progect.GrassCutterShop.service.OrderService;
import com.progect.GrassCutterShop.service.ProductService;
import com.progect.GrassCutterShop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UserCommand {
    public static void detailOrder(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        Product product = ProductService.getInstance().findProductById(productId).get();
        request.setAttribute("product", product);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/oneProductPage.jsp").forward(request, response);
    }
    public static void deleteOrderSubmit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DaoException, IOException {
        OrderService.getInstance().deleteOrder(Long.valueOf(request.getParameter("orderId")));
        response.sendRedirect("/user/userOrders");
    }
    public static void userOrders(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List <Order> orderList = OrderService.getInstance().getOrderListByUserId(user.getUserId());
        request.setAttribute("orderList", orderList);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/orderList.jsp").forward(request, response);
    }
    public static void editUserByUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("updateUser", user);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/editUser.jsp").forward(request, response);
    }
    public static void editUserByUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException, SQLException, IOException {
        UserService.getInstance().updateUser(buildUpdateUser(request));
        User user = UserService.getInstance().findUserByUserId(Long.valueOf(request.getParameter("id")));
        request.getSession().setAttribute("user", user);
        response.sendRedirect("/user/listOfProducts");
    }
    public static void listOfProducts(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        List<Product> listOfProducts = ProductService.getInstance().findAllProducts();
        request.setAttribute("listOfProducts", listOfProducts);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/productsList.jsp").forward(request,response);
    }
    public static void addUserOrderSubmit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long userId = Long.parseLong(String.valueOf(user.getUserId()));
        Long productId = Long.parseLong((request.getParameter("productId")));
        Product product = ProductService.getInstance().findProductById(productId).get();
        LocalDateTime orderTime = LocalDateTime.now();

        Order order = OrderService.getInstance().makeOrder(userId, productId, product.getTitle(), orderTime, product.getPrice());
        OrderService.getInstance().saveOrder(order);
        response.sendRedirect("/user/listOfProducts");
    }



    private static User buildUpdateUser(HttpServletRequest request) {
        return User.builder()
                .userId(Long.valueOf(request.getParameter("id")))
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .login(request.getParameter("login"))
                .password(request.getParameter("password"))
                .role(UserRole.valueOf(request.getParameter("role")))
                .build();
    }
}
