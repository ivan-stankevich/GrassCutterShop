package com.progect.GrassCutterShop.controllers.basic;

import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.UserRole;
import com.progect.GrassCutterShop.exception.ServiceException;
import com.progect.GrassCutterShop.service.UserService;
import com.progect.GrassCutterShop.util.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class BasicCommand {
    public static void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect("/login");
    }
    public static void registrationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
    }
    public static void registrationSubmit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserRole userRole = UserRole.USER_ROLE;

        User user = UserService.getInstance().registrationUser(firstName, lastName, login, PasswordEncoder.encodePassword(password), userRole);
        int role = user.getRole().ordinal();

        request.getSession().setAttribute("role", role);
        request.getSession().setAttribute("user", user);
        response.sendRedirect("/user/listOfProducts");
    }
    public static void userLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
    public static void userLoginSubmit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = UserService.getInstance().findUserDTOByLoginAndPassword(login, PasswordEncoder.encodePassword(password));
        // int role = user.getRole().ordinal();
        //request.getSession().setAttribute("role", role);
        request.getSession().setAttribute("user", user);
        switch (user.getRole()) {
            case USER_ROLE:
                response.sendRedirect("/user/listOfProducts");
                break;
            case ADMIN_ROLE:
                response.sendRedirect("/admin/listOfUsers");
                break;
            default:
                response.sendRedirect("/login");
        }
    }
}
