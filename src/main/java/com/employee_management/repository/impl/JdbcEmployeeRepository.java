package com.employee_management.repository.impl;

import com.employee_management.repository.EmployeeRepository;
import com.employee_management.repository.query.SqlQuery;
import com.employee_management.model.*;
import com.employee_management.util.connection.DbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcEmployeeRepository implements EmployeeRepository {
    @Override
    public List<Employee> findAllEmployee() {
        List<Employee> employee_list = new ArrayList<>();
        String sql = SqlQuery.GET_ALL_EMPLOYEES_TEST.getQuery();
        Connection connection = DbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_name")
                );
                employee.setEmployeeId(rs.getInt("employee_id"));
                String tab_number = rs.getString("tab_number");
                employee.setTabNumber((tab_number != null && !tab_number.isEmpty()) ? tab_number : null);
                employee.setDepartment(new Department(rs.getString("department_name")));
                employee.setPosition(new Position(rs.getString("position_name")));
                String shift = rs.getString("shift");
                employee.setShift((shift != null && !shift.isEmpty()) ? shift : null);
                employee.setBirthDate(Optional.ofNullable(rs.getDate("birth_date"))
                        .map(java.sql.Date::toLocalDate)
                        .orElse(null));
                employee.setPhotoPath(rs.getString("photo_path"));
                employee.setHireDate(Optional.ofNullable(rs.getDate("hire_date"))
                        .map(java.sql.Date::toLocalDate)
                        .orElse(null));
                employee.setTerminationDate(Optional.ofNullable(rs.getTimestamp("termination_date"))
                        .map(Timestamp::toLocalDateTime)
                        .orElse(null));
                employee.setCreateAt(rs.getObject("created_at", LocalDateTime.class));
                employee.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                employee.setActive(rs.getBoolean("is_active"));
                employee.setDeletedAt(Optional.ofNullable(rs.getTimestamp("deleted_at"))
                        .map(Timestamp::toLocalDateTime)
                        .orElse(null));
                employee_list.add(employee);
            }
            connection.close();
            findAllEmployeesWithContacts(employee_list);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
        return employee_list;
    }

    @Override
    public Employee findEmployeeById(int id) {
        return null;
    }

    @Override
    public void findAllEmployeesWithContacts(List<Employee> employeesDto) {
        if (employeesDto.isEmpty()) return;
        Map<Integer, Employee> employeeById = employeesDto.stream()
                .collect(Collectors.toMap(Employee::getEmployeeId, emp -> emp));
        List<Integer> ids = employeesDto.stream().map(Employee::getEmployeeId).toList();
        String placeholder = String.join(",",
                Collections.nCopies(ids.size(), "?"));
        String sql = SqlQuery.GET_ALL_EMPLOYEES_CONTACT.getQuery().formatted(placeholder, placeholder);

        Connection connection = DbConnection.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int paramIndex = 1;

            for(Integer id : ids)preparedStatement.setInt(paramIndex++, id);
            for(Integer id : ids) preparedStatement.setInt(paramIndex++, id);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Integer employeeId = rs.getInt("employee_id");
                    String source = rs.getString("source");
                    String value = rs.getString("value");
                    String type = rs.getString("type");
                    boolean isMain = rs.getBoolean("is_main");
                    boolean isActive = rs.getBoolean("is_active");

                    Employee emp = employeeById.get(employeeId);
                    if (emp == null) continue;

                    switch (source) {
                        case "phone":
                            Phone phone = new Phone(value, ContactType.fromTitle(type), isMain, isActive);
                            emp.setPhone(phone);
                            break;
                        case "email":
                            Email email = new Email(value, ContactType.fromTitle(type), isMain, isActive);
                            emp.setEmail(email);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
    }
}