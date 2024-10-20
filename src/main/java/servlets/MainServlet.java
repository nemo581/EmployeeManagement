package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeeManagement;
import service.message.Title;
import service.message.WriteMessage;

import java.io.IOException;

@WebServlet(urlPatterns = {"/employees_list"})
public class MainServlet extends HttpServlet {
    private final WriteMessage message = new WriteMessage();
    private static EmployeeManagement employeeManagement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        employeeManagement = new EmployeeManagement();
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

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
