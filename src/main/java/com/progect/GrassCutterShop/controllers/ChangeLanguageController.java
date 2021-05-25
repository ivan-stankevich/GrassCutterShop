package com.progect.GrassCutterShop.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changeLanguage")
public class ChangeLanguageController extends HttpServlet {

    private static final String LANGUAGE = "language";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String referer = req.getHeader("referer");
        String language = req.getParameter(LANGUAGE);
        req.getSession().setAttribute("language", language);
        resp.sendRedirect(referer);

    }
}
