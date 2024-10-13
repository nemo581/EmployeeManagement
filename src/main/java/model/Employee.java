package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Employee {
    private final List<LocalDate> employeeWorkDays = new ArrayList<>();
    private final LinkedHashMap<LocalDate, Integer> testDate = new LinkedHashMap<>();
    private final String firstName;
    private final String lustName;
    private final String fatherName;
    private int shift;
    private int marker;

    public Employee(String firstName, String lustName, String fatherName, int shift) {
        this.firstName = firstName;
        this.lustName = lustName;
        this.fatherName = fatherName;
        this.shift = shift;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLustName() {
        return lustName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public int getShift() {
        return shift;
    }

    public int getMarker() {
        return marker;
    }

    public List<LocalDate> getEmployeeWorkDays() {
        return employeeWorkDays;
    }

    public Map<LocalDate, Integer> getTestDate() {
        return this.testDate;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public void setEmployeeWorkDays(LocalDate date) {
        this.employeeWorkDays.add(date);
    }

    public void setTestDate(LocalDate date, int mark) {
        this.testDate.put(date, mark);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lustName='" + lustName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", shift=" + shift +
                ", marker=" + marker +'}';
    }
}