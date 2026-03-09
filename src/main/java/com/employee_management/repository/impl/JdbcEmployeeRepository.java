package com.employee_management.repository.impl;

import com.employee_management.model.*;
import com.employee_management.repository.EmployeeRepository;
import com.employee_management.repository.query.SqlQuery;
import com.employee_management.util.connection.DbConnection;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcEmployeeRepository implements EmployeeRepository {
    @Override
    public List<Employee> findAllEmployee() {
        List<Employee> employee_list = new ArrayList<>();
        String sql = SqlQuery.GET_ALL_EMPLOYEES.getQuery();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
                employee.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                employee.setPhotoPath(rs.getString("photo_path"));
                employee.setHireDate(rs.getObject("hire_date", LocalDate.class));
                employee.setTerminationDate(rs.getObject("termination_date", LocalDateTime.class));
                employee.setCreateAt(rs.getObject("created_at", LocalDateTime.class));
                employee.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                employee.setActive(rs.getBoolean("is_active"));
                employee.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
                employee_list.add(employee);
            }
            findAllEmployeesWithContacts(employee_list);
            for (Employee empl : employee_list) {
                System.out.println(empl);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee_list;
    }

    @Override
    public Employee findEmployeeById(int id) {
        Employee employee = null;
        String sql = SqlQuery.GET_EMPLOYEE_BY_ID.getQuery();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
//                    employee = new Employee(rs.getString("first_name"),
//                            rs.getString("last_name"),
//                            rs.getString("middle_name"));
//                    employee.setEmployeeId(id);
                    employee = new Employee();
                    employee.setEmployeeId(rs.getInt("employee_id"));
                    employee.setTabNumber(rs.getString("tab_number"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setMiddleName(rs.getString("middle_name"));
                    employee.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                    employee.setPhotoPath(rs.getString("photo_path"));
                    employee.setShift(rs.getString("shift"));
                    employee.setHireDate(rs.getObject("hire_date", LocalDate.class));
                    employee.setTerminationDate(rs.getObject("termination_date", LocalDateTime.class));
                    employee.setCreateAt(rs.getObject("created_at", LocalDateTime.class));
                    employee.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                    employee.setActive(rs.getBoolean("is_active"));
                    employee.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        if (employee != null) {
            List<Phone> employeePhones = findPhoneByEmployeeId(id);
            for (Phone phone : employeePhones) {
                employee.setPhone(phone);
            }
            List<Email> employeeEmails = findEmailByEmployeeId(id);
            for (Email email : employeeEmails) {
                employee.setEmail(email);
            }
        }
        System.out.println(">>" + employee);
        return employee;
    }
    @Override
    public List<Phone> findPhoneByEmployeeId(int employeeId) {
        List<Phone> employeePhones = new ArrayList<>();
        String sql = SqlQuery.GET_PHONE_BY_EMPLOYEE_ID.getQuery();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Phone employeePhone = new Phone();
                    employeePhone.setId(rs.getInt("id"));
                    employeePhone.setPhone(rs.getString("phone"));
                    employeePhone.setContactType(ContactType.fromTitle(rs.getString("type")));
                    employeePhone.setMain(rs.getBoolean("is_main"));
                    employeePhone.setActive(rs.getBoolean("is_active"));
                    employeePhone.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    employeePhone.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                    employeePhone.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
                    employeePhone.setEmployeeId(rs.getInt("employee_id"));
                    employeePhones.add(employeePhone);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeePhones;
    }

    @Override
    public List<Email> findEmailByEmployeeId(int employeeId) {
        List<Email> employeeEmails = new ArrayList<>();
        String sql = SqlQuery.GET_EMAIL_BY_EMPLOYEE_ID.getQuery();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Email employeeEmail = new Email();
                    employeeEmail.setId(rs.getInt("id"));
                    employeeEmail.setEmail(rs.getString("email"));
                    employeeEmail.setMain(rs.getBoolean("is_main"));
                    employeeEmail.setContactType(ContactType.fromTitle(rs.getString("type")));
                    employeeEmail.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    employeeEmail.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                    employeeEmail.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
                    employeeEmail.setEmployeeId(rs.getInt("employee_id"));
                    employeeEmails.add(employeeEmail);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeEmails;
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
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            for (Integer id : ids) preparedStatement.setInt(paramIndex++, id);
            for (Integer id : ids) preparedStatement.setInt(paramIndex++, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
    }
}