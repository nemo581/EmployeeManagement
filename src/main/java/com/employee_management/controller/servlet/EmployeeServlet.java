package com.employee_management.controller.servlet;

import com.employee_management.model.Employee;
import com.employee_management.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/employee/*"})
public class EmployeeServlet extends HttpServlet {
    Employee employee;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("employee servlet");
        String pathInfo = req.getPathInfo();
        System.out.println("pathInfo = " + pathInfo);
        if (pathInfo != null && pathInfo.length() > 1) {
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);
            System.out.println("id = " + id);
            employee = EmployeeService.getEmployeeById(id);
            System.out.println(employee);
        }
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(req, resp);
    }
}