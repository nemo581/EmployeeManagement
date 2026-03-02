package com.employee_management.service;

import com.employee_management.repository.impl.JdbcEmployeeRepository;
import com.employee_management.repository.EmployeeRepository;
import com.employee_management.model.Employee;
import java.util.List;

public class EmployeeService {
    private static final EmployeeRepository employeeRepository = new JdbcEmployeeRepository();
    public static List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployee();
    }
    public static Employee getEmployeeById(int id) {
        return employeeRepository.findEmployeeById(id);
    }
}