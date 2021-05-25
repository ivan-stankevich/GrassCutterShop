package com.progect.GrassCutterShop.filter;

import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (isUserAuthorized(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            sendAtLoginPage (httpServletRequest, httpServletResponse);
        }
    }

    private boolean isUserAuthorized (HttpServletRequest httpServletRequest) {
        boolean result = false;
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user != null && user.getRole() == UserRole.ADMIN_ROLE) {
            result = true;
        }
        return result;
    }

    private void sendAtLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("/login");
    }

    @Override
    public void destroy() {

    }
}
