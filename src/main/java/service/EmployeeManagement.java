package service;

import model.Employee;
import repository.EmployeeData;

import java.time.LocalDate;
import java.util.List;

public class EmployeeManagement {
    private final EmployeeData empData;

    public EmployeeManagement() {
        this.empData = new EmployeeData("/employee.txt");
        WorkSchedule workSchedule = new WorkSchedule(LocalDate.of(2024, 10, 1));
        workSchedule.getWorkSchedule(empData);
    }

    public List<Employee> getEmployeeList() {
        return empData.getEmployeeList();
    }
}