package main;

import dao.EmployeeDB;
import model.Employee;

import java.util.concurrent.CopyOnWriteArrayList;

public class EmployeeManager {
    private final EmployeeDB employeeDB = new EmployeeDB();

    public CopyOnWriteArrayList<Employee> getAllEmployees() {
        return employeeDB.getAllEmployees();
    }
}
