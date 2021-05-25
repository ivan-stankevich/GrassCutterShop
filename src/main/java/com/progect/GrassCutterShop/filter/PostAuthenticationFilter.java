package com.progect.GrassCutterShop.filter;

import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = {"/login", "/registration"})
public class PostAuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)  servletResponse;

        if (!isUserAuthorized(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            User user = (User) httpServletRequest.getSession().getAttribute("user");
            switch (user.getRole()) {
                case USER_ROLE:
                    httpServletResponse.sendRedirect("/user/listOfProducts");
                    break;
                case ADMIN_ROLE:
                    httpServletResponse.sendRedirect("/admin/listOfUsers");
                    break;
                default:
                    throw new EnumConstantNotPresentException(UserRole.class, user.getRole().name());
            }
        }
    }

    private boolean isUserAuthorized(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        return Objects.nonNull(user);
    }

    @Override
    public void destroy() {

    }
}
