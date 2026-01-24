package com.EmployeeManagement.dao.interface_dao;

import com.EmployeeManagement.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> findAllEmployee();
    public Employee findEmployeeById(int id);
    public void addEmployee(Employee employee);
}
