package com.EmployeeManagement.service;

import com.EmployeeManagement.dao.EmployeeRepositoryImpl;
import com.EmployeeManagement.dao.interface_dao.EmployeeRepository;
import com.EmployeeManagement.model.Employee;
import java.util.List;

public class EmployeeService {
    EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployee();
    }
}