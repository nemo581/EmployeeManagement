package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.EmployeeManagement;
import service.message.WriteMessage;

import java.io.IOException;

@WebServlet(urlPatterns = {"/work_schedule"})
public class WorkScheduleServlet extends HttpServlet {
    private final WriteMessage message = new WriteMessage();
    private static EmployeeManagement employeeManagement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        employeeManagement = new EmployeeManagement();
//        employeeManagement.getEmployeeSchedule();

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[INFO] WorkScheduleServletDoGet()");
        req.setAttribute("work_schedule", employeeManagement.getEmployeeSchedule());
        getServletContext().getRequestDispatcher("/work_schedule.jsp").forward(req, resp);
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
