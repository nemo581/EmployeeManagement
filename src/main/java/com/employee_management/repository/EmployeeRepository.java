package com.employee_management.repository;

import com.employee_management.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> findAllEmployee();
    public Employee findEmployeeById(int id);
    public void findAllEmployeesWithContacts(List<Employee> employeesDto);
    public void addEmployee(Employee employee);
}
