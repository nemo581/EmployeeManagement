package com.employee_management.controller.servlet;

import com.employee_management.model.Position;
import com.employee_management.repository.DepartmentRepository;
import com.employee_management.model.Department;
import com.employee_management.repository.PositionDepartmentRepository;
import com.employee_management.repository.PositionRepository;
import com.employee_management.repository.impl.JdbcDepartmentRepository;
import com.employee_management.repository.impl.JdbcPositionDepartmentRepository;
import com.employee_management.repository.impl.JdbcPositionRepository;
import com.employee_management.service.DepartmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/department_management"})
public class DepartmentManagementServlet extends HttpServlet {
    private ObjectMapper mapper;
    private DepartmentRepository departmentRepository;
    private PositionRepository positionRepository;
    private PositionDepartmentRepository positionDepartmentRepository;

    @Override
    public void init() throws ServletException {
        this.mapper = new ObjectMapper();
        this.departmentRepository = new JdbcDepartmentRepository();
        this.positionRepository = new JdbcPositionRepository();
        this.positionDepartmentRepository = new JdbcPositionDepartmentRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet вызван, Content-Type: " + req.getContentType());
        System.out.println("Raw body: " + req.getReader().lines().collect(Collectors.joining()));
        List<Department> departments = departmentRepository.getAllDepartments();
        log("Fetched departments: " + departments.size());
        req.setAttribute("departments", departments);
        req.getRequestDispatcher("/WEB-INF/jsp/manage_department.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost вызван, Content-Type: " + req.getContentType());
//        System.out.println("Raw body: " + req.getReader().lines().collect(Collectors.joining()));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            JsonNode jsonNode = mapper.readTree(req.getInputStream());
            String action = jsonNode.path("action").asText(null);
            System.out.println("action: " + action);
            if ("add_position_to_dept".equals(action)) {
                handleAddPositionToDepartment(jsonNode, resp);
            } else {
                handleAddDepartment(jsonNode, resp);
            }
        } catch (IOException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Ошибка парсинга JSON: " + e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сервера: " + e.getMessage());
        }
    }

    private void handleAddDepartment(JsonNode jsonNode, HttpServletResponse resp) throws IOException {
        System.out.println( "[" + LocalTime.now() + "] handleAddDepartment: " + jsonNode);
        try {
            Department department = mapper.treeToValue(jsonNode, Department.class);
            System.out.println( "[" + LocalTime.now() + "] department: " + department);
            if (department.getName() == null || department.getName().trim().isEmpty()) {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Название департамента обязательно!");
                return;
            }
            String name = department.getName().trim();
            log("Add department: " + name);
            int id = DepartmentService.addDepartment(department);
            System.out.println( "[" + LocalTime.now() + "] department: " + department);
            sendSuccessResponse(resp, "id", id, "name", name);
        } catch (IOException e) {
            log("handleAddDepartment error: " + e.getMessage());
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка обработки запроса!" + e.getMessage());
        }
    }

    private void handleAddPositionToDepartment(JsonNode jsonNode, HttpServletResponse resp) throws IOException {
        System.out.println( "[" + LocalTime.now() + "] handleAddPositionToDepartment: " + jsonNode);
        try {
            Position position = mapper.treeToValue(jsonNode, Position.class);
            System.out.println( "[" + LocalTime.now() + "] position: " + position);
            if (position.getDepartmentId() == null || position.getName() == null || position.getName().trim().isEmpty()) {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Обязательные поля: department_id и position_name!");
                return;
            }
            if (position.getSalary() != null && position.getSalary().compareTo(BigDecimal.ZERO) < 0) {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Зарплата не может быть отрицательной!");
                return;
            }
            String positionName = position.getName().trim();
            log("Adding position: " + positionName + " to department " + position.getDepartmentId());
            int positionId = positionRepository.addPosition(positionName, position.getSalary());
            positionDepartmentRepository.linkPositionToDepartment(position.getDepartmentId(), positionId);
            sendSuccessResponse(resp, "position_id", positionId, "message", "Должность успешно добавлена и привязана к департаменту!");
        } catch (Exception e) {
            log("handleAddPositionToDepartment error: " + e.getMessage());
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка обработки запроса!" + e.getMessage());
        }
    }


    private void sendSuccessResponse(HttpServletResponse resp, String key_1, Object value_1, String key_2, Object value_2) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        mapper.writeValue(resp.getWriter(), Map.of("success", true, key_1, value_1, key_2, value_2));
    }

    private void sendErrorResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        mapper.writeValue(resp.getWriter(), Map.of("success", false, "message", message));
    }
}