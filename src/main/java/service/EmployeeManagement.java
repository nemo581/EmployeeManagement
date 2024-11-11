package service;

import model.Employee;
import repository.EmployeeData;

import java.time.LocalDate;
import java.util.List;

public class EmployeeManagement {
    private final EmployeeData empData = new EmployeeData();

    public EmployeeManagement() {
        if (LocalDate.now().getMonth().getValue() == 12 && LocalDate.now().getDayOfMonth() == 1) {
            empData.updateWorkSchedule();
        }
    }

    public List<Employee> getEmployeeList() {
        return empData.getAllEmployees();
    }

    public List<Employee> getEmployeeSchedule() {
        return empData.getAllWorkSchedule();
    }

//    public List<Employee> getEmployeeList() {
//        return empData.getEmployeeList();
//    }
//
//    public void getEmployeeSchedule() {
//        System.out.println("[INFO] getEmployeeSchedule()");
//        empData.getAllWorkSchedule();
//    }
}