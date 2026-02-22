package com.employee_management.controller.servlet;

import com.employee_management.model.Employee;
import com.employee_management.service.EmployeeService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@WebServlet(urlPatterns = {"/employees_schedule"})
public class EmployeesScheduleServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employeeList = EmployeeService.getAllEmployees();
        LocalDate date = LocalDate.of(2026, 2, 1);
        LocalDate temp_date = date;
        String[][] days = new String[date.lengthOfMonth()][2];
        for (int i = 0; i < days.length; i++) {
            days[i][0] = String.valueOf(i + 1);
            days[i][1] = temp_date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru", "Ru"));
            temp_date = temp_date.plusDays(1);
        }
        temp_date = null;
        req.setAttribute("daysInMonth", days.length);
        req.setAttribute("year", date.getYear());
        req.setAttribute("month", date.getMonth().getDisplayName(TextStyle.FULL, Locale.of("ru", "Ru")));
        req.setAttribute("matrix_days", days);
        req.setAttribute("employees", employeeList);
        req.getRequestDispatcher("/WEB-INF/jsp/employees_schedule.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}