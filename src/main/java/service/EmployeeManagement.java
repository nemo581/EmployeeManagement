package service;

import model.Employee;
import repository.EmployeeData;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeManagement {
    private final EmployeeData empData;

    public EmployeeManagement() {
        this.empData = new EmployeeData();
        WorkSchedule workSchedule = new WorkSchedule(LocalDate.of(2025, 1, 1));
        workSchedule.getWorkSchedule(empData);
    }

    public List<Employee> getEmployeeList() {
        return empData.getEmployeeList();
    }
}