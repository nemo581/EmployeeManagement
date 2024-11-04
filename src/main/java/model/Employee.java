package model;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

public class Employee {
    private final LinkedHashMap<LocalDate, Integer> employeeWorkDays = new LinkedHashMap<>();
    private final String firstName;
    private final String lustName;
    private final String fatherName;
    private Month month;
    private int year;
    private int shift;
    private int marker;
    private int id;

    public Employee(int id, String firstName, String lustName, String fatherName, int shift) {
        this.id = id;
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

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getShift() {
        return shift;
    }

    public int getMarker() {
        return marker;
    }

    public Map<LocalDate, Integer> getEmployeeWorkDays() {
        return employeeWorkDays;
    }
    public int getId() {
        return this.id;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public void setEmployeeWorkDays(LocalDate date, int mark) {
        this.employeeWorkDays.put(date, mark);
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lustName='" + lustName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", shift=" + shift +
                ", marker=" + marker + '}';
    }
}