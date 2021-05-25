package com.progect.GrassCutterShop.controllers.admin;

import com.progect.GrassCutterShop.entity.Order;
import com.progect.GrassCutterShop.entity.Product;
import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.Engine;
import com.progect.GrassCutterShop.entity.enums.Manufacturer;
import com.progect.GrassCutterShop.entity.enums.Type;
import com.progect.GrassCutterShop.entity.enums.UserRole;
import com.progect.GrassCutterShop.exception.ServiceException;
import com.progect.GrassCutterShop.service.OrderService;
import com.progect.GrassCutterShop.service.ProductService;
import com.progect.GrassCutterShop.service.UserService;
import com.progect.GrassCutterShop.util.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AdminCommand {
    public static void newUserSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserRole userRole = UserRole.USER_ROLE;
        User user = UserService.getInstance().registrationUser(firstName, lastName, login, PasswordEncoder.encodePassword(password), userRole);
        response.sendRedirect("/admin/listOfUsers");
    }
    public static void editUserByAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = UserService.getInstance().findUserByUserId(id);
        request.setAttribute("updateUser", user);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/editUser.jsp").forward(request, response);
    }
    public static void editUserByAdmin(HttpServletRequest request, HttpServletResponse response) throws ServiceException, SQLException, IOException {
        UserService.getInstance().updateUser(buildUpdateUser(request));
        response.sendRedirect("/admin/listOfUsers");
    }
    public static void deleteUserSubmit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        UserService.getInstance().deleteUser(id);
        response.sendRedirect("/admin/listOfUsers");
    }
    public static void listUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, ServiceException {
        List<User> listOfUsers = UserService.getInstance().findAllUsers();
        request.setAttribute("listOfUsers", listOfUsers);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/usersList.jsp").forward(request,response);
    }
    public static void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServiceException {
        Long id = Long.parseLong(request.getParameter("id"));
        ProductService.getInstance().deleteProduct(id);
        response.sendRedirect("/user/listOfProducts");
    }

    public static void newProductSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        ProductService.getInstance().addProduct(buildNewProduct(request));
        response.sendRedirect("/user/listOfProducts");
    }

    public static void updateProductPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
        Long productId = Long.parseLong(request.getParameter("id"));
        Product product = ProductService.getInstance().findProductById(productId).get();
        request.setAttribute("grassCutter", product);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/newProduct.jsp").forward(request, response);
    }

    public static void updateProductSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        ProductService.getInstance().updateProduct(buildUpdateProduct(request));
        response.sendRedirect("/user/listOfProducts");
    }

    public static void allOrdersSubmit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        List <Order> orderList = OrderService.getInstance().getAllOrders();
        request.setAttribute("orderList", orderList);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/orderList.jsp").forward(request, response);
    }

    public static void newProductPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/newProduct.jsp").forward(request, response);
    }

    private static Product buildUpdateProduct(HttpServletRequest request) {
        String str = request.getParameter("price").replace(',','.');
        return Product.builder()
                .productId(Long.valueOf(request.getParameter("id")))
                .title(request.getParameter("title"))
                .manufacturer(Manufacturer.valueOf(request.getParameter("manufacturer")))
                .type(Type.valueOf(request.getParameter("type")))
                .engine(Engine.valueOf(request.getParameter("engine")))
                .price(new BigDecimal((str)))
                .build();
    }

    private static Product buildNewProduct(HttpServletRequest request) {
        String str = request.getParameter("price").replace(',','.');
        return Product.builder()
                .title(request.getParameter("title"))
                .manufacturer(Manufacturer.valueOf(request.getParameter("manufacturer")))
                .type(Type.valueOf(request.getParameter("type")))
                .engine(Engine.valueOf(request.getParameter("engine")))
                .price(new BigDecimal((str)))
                .build();
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
