package com.employee_management.controller.servlet;

import com.employee_management.model.Employee;
import com.employee_management.service.DepartmentService;
import com.employee_management.service.EmployeeService;
import com.employee_management.service.PositionService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/employee"})
public class EmployeeServlet extends HttpServlet {
    Employee employee;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("employee servlet");
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        System.out.println("action = " + action + "\n" +
                           "idParam = " + idParam + "\n");
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID должен быть числом");
            return;
        }
        employee = EmployeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("error id: " + id + " not founded");
            resp.sendError(404);
            return;
        }
        System.out.println(employee);
        req.setAttribute("employee", employee);

        switch (action) {
            case "info":
                req.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(req, resp);
                break;
            case "edit":
                req.setAttribute("departments", DepartmentService.getAllDepartments());
                System.out.println("departments: " + DepartmentService.getAllDepartments() + " : " + req.getParameter("departments"));
                req.setAttribute("positions", PositionService.getAllPositions());
                System.out.println("Positions: " + PositionService.getAllPositions() + " : " + req.getParameter("positions"));
                req.getRequestDispatcher("/WEB-INF/jsp/employee_edit.jsp").forward(req, resp);
        }
    }
}