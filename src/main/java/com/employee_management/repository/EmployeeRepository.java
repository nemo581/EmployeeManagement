package com.employee_management.repository;

import com.employee_management.model.Email;
import com.employee_management.model.Employee;
import com.employee_management.model.Phone;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> findAllEmployee();
    public Employee findEmployeeById(int id);
    public void findAllEmployeesWithContacts(List<Employee> employeesDto);
    public List<Phone> findPhoneByEmployeeId(int employeeId);
    public List<Email> findEmailByEmployeeId(int employeeId);
    public void addEmployee(Employee employee);
}
