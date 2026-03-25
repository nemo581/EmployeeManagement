package com.employee_management.repository;

import com.employee_management.model.Email;
import com.employee_management.model.Employee;
import com.employee_management.model.Phone;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> findAllEmployees();
    public Employee findEmployeeById(Integer id);
    public void findAllEmployeesContacts(List<Employee> employeesDto);
    public void findEmployeesContactsById(Employee employee);
    public List<Phone> findPhoneByEmployeeId(Integer employeeId);
    public List<Email> findEmailByEmployeeId(Integer employeeId);
    public void addEmployee(Employee employee);
}
