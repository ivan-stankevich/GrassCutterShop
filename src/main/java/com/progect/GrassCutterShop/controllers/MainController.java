package com.progect.GrassCutterShop.controllers;

import com.progect.GrassCutterShop.controllers.admin.AdminCommand;
import com.progect.GrassCutterShop.controllers.basic.BasicCommand;
import com.progect.GrassCutterShop.controllers.user.UserCommand;
import com.progect.GrassCutterShop.entity.Order;
import com.progect.GrassCutterShop.entity.Product;
import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.Engine;
import com.progect.GrassCutterShop.entity.enums.Manufacturer;
import com.progect.GrassCutterShop.entity.enums.Type;
import com.progect.GrassCutterShop.entity.enums.UserRole;
import com.progect.GrassCutterShop.exception.DaoException;
import com.progect.GrassCutterShop.exception.ServiceException;
import com.progect.GrassCutterShop.service.OrderService;
import com.progect.GrassCutterShop.service.ProductService;
import com.progect.GrassCutterShop.service.UserService;
import com.progect.GrassCutterShop.util.PasswordEncoder;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
@SuppressWarnings("Duplicates")
@WebServlet("/")
public class MainController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
            switch (action) {
                case "/login":
                    BasicCommand.userLoginPage(request, response);
                    break;
                case "/login/submit":
                    BasicCommand.userLoginSubmit(request, response);
                    break;
                case "/registration":
                    BasicCommand.registrationPage(request, response);
                    break;
                case "/registration/submit":
                    BasicCommand.registrationSubmit(request, response);
                    break;
                case "/logout":
                    BasicCommand.logout(request, response);
                    break;
                case "/admin/newUser":
                    BasicCommand.registrationPage(request, response);
                    break;
                case "/admin/newUser/submit":
                    AdminCommand.newUserSubmit(request, response);
                    break;
                case "/admin/editUser":
                    AdminCommand.editUserByAdminPage(request, response);
                    break;
                case "/admin/editUser/submit":
                    AdminCommand.editUserByAdmin(request, response);
                    break;
                case "/user/editUser":
                    UserCommand.editUserByUserPage(request, response);
                    break;
                case "/user/editUser/submit":
                    UserCommand.editUserByUser(request, response);
                    break;
                case "/admin/deleteUser/submit":
                    AdminCommand.deleteUserSubmit(request, response);
                    break;
                case "/user/listOfProducts":
                    UserCommand.listOfProducts(request, response);
                    break;
                case "/admin/listOfUsers":
                    AdminCommand.listUser(request, response);
                    break;
                case "/admin/deleteProduct":
                    AdminCommand.deleteProduct(request, response);
                    break;
                case "/admin/newProduct":
                    AdminCommand.newProductPage(request, response);
                    break;
                case "/admin/newProduct/submit":
                    AdminCommand.newProductSubmit(request, response);
                    break;
                case "/admin/updateProduct":
                    AdminCommand.updateProductPage(request, response);
                    break;
                case "/admin/updateProduct/submit":
                    AdminCommand.updateProductSubmit(request, response);
                    break;
                case "/user/addOrder/submit":
                    UserCommand.addUserOrderSubmit(request, response);
                    break;
                case "/user/userOrders":
                    UserCommand.userOrders(request, response);
                    break;
                case "/admin/allOrders":
                    AdminCommand.allOrdersSubmit(request, response);
                    break;
                case "/user/deleteOrder":
                    UserCommand.deleteOrderSubmit(request, response);
                    break;
                case "/user/detailOrder":
                    UserCommand.detailOrder(request,response);
                default:
                    BasicCommand.userLoginPage(request, response);
                    break;
            }
    }
}
