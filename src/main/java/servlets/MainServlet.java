package servlets;

import service.EmployeeManagement;
import service.message.Title;
import service.message.WriteMessage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/employees_list"})
public class MainServlet extends HttpServlet {
    private final WriteMessage message = new WriteMessage();
    private static EmployeeManagement employeeManagement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        employeeManagement = new EmployeeManagement();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        System.out.println(req.getContextPath());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employee_list", employeeManagement.getEmployeeList());
        getServletContext().getRequestDispatcher("/list_of_employees.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        message.printMessage("doPost()", Title.INFO);
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        message.printMessage("destroy()", Title.INFO);
        super.destroy();
    }
}