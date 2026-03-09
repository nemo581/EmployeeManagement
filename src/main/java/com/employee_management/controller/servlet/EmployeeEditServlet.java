package com.employee_management.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/employee/edit", "/employee/update"})
public class EmployeeEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String id = req.getParameter("id");
        System.out.println("edit.... pathInfo = " + pathInfo + "\n id= " + id);
        req.getRequestDispatcher("/WEB-INF/jsp/employee_edit.jsp").forward(req, resp);
    }
}
