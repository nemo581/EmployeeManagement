package servlets;

import management.EmployeeManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = {"/employees"})
public class EmployeeListServlet  extends HttpServlet {
    private EmployeeManager empManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        empManager = new EmployeeManager();
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS")) + " " + this.getClass());
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employee_list", empManager.getAllEmployees());
        getServletContext().getRequestDispatcher("/employee_list.jsp").forward(req, resp);
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
