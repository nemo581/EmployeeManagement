package com.employee_management.controller.servlet;

import com.employee_management.model.Employee;
import com.employee_management.service.EmployeeService;
import com.employee_management.util.LogUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(urlPatterns = {"/all_employees"})
public class AllEmployeesServlet extends HttpServlet {
    List<Employee> employeeList;

    @Override
    public void init() throws ServletException {
        employeeList = EmployeeService.getAllEmployees();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogUtil.logRequest(req, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "ReceivedEmployees:/" + employeeList.size());
        req.setAttribute("ip", req.getRemoteAddr());
        req.setAttribute("session", req.getSession().getId());
        req.setAttribute("protocol", req.getProtocol());
        req.setAttribute("reqUri", req.getRequestURI());
        req.setAttribute("serverPort", req.getServerPort());
        req.setAttribute("referer", req.getHeader("Referer"));
        req.setAttribute("method", req.getMethod());
        req.setAttribute("locale", req.getLocale().getLanguage());
        req.setAttribute("month", LocalDateTime.now().getMonth());
        req.setAttribute("year", LocalDateTime.now().getYear());
        req.setAttribute("employees", employeeList);
        req.getRequestDispatcher("/WEB-INF/jsp/all_employees.jsp").forward(req, resp);
        LogUtil.logRequest(req, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "SentEmployees:/" + employeeList.size(), "ResponseStatus:/" + resp.getStatus());
    }
}