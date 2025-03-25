package model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Employee {
    private final HashMap<Month, Byte> employeeWorkDays = new LinkedHashMap<>();
    private String department;
    private String position;
    private String firstName;
    private String lastName;
    private String fatherName;
    private LocalDate dayOfBirth;
    private String[][] phoneNumbers;
    private String email;
    private String serviceNumber;
    private String shift;
    private int id;

    public void addEmployee(Employee employee) {

    }

    public void deleteEmployee(Employee employee) {

    }

    //Ежегодный отпуск
    public void annualLeave(Employee employee) {
    }

    public void sickDay(Employee employee) {

    }

    //Отсутствие сотрудника: за свой счет/подмена сотрудника другим сотрудником
    public void absenceOfAnEmployee(Employee employee) {

    }

    public Employee(String department, String position, String firstName,
                    String lastName, String fatherName, LocalDate dayOfBirth,
                    String[][] phoneNumbers, String serviceNumber, String shift) {
        this.department = department;
        this.position = position;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.dayOfBirth = dayOfBirth;
        this.phoneNumbers = phoneNumbers;
        this.serviceNumber = serviceNumber;
        this.shift = shift;
    }

    public Employee(String department, String position, String firstName,
                    String lastName, String fatherName, LocalDate dayOfBirth,
                    String[][] phoneNumbers) {
        this.department = department;
        this.position = position;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.dayOfBirth = dayOfBirth;
        this.phoneNumbers = phoneNumbers;
    }


//    public Employee(String department, String position, String firstName,
//                    String lastName, String fatherName, LocalDate dayOfBirth,
//                    String[][] phoneNumbers, String serviceNumber) {
//        this.department = department;
//        this.position = position;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.fatherName = fatherName;
//        this.dayOfBirth = dayOfBirth;
//        this.phoneNumbers = phoneNumbers;
//        this.serviceNumber = serviceNumber;
//    }

    public Employee(String department, String position, String firstName,
                    String lastName, String fatherName, String shift, int id) {
        this.department = department;
        this.position = position;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.shift = shift;
        this.id = id;
    }

    public void setEmployeeWorkDays(Month month, Byte marker) {
        this.employeeWorkDays.put(month, marker);
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }
    public void setPhoneNumbers(String[][] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }
    public void setShift(String shift) {
        this.shift = shift;
    }

    public HashMap<Month, Byte> getEmployeeWorkDays() {
        return employeeWorkDays;
    }
    public String getDepartment() {
        return department;
    }
    public String getPosition() {
        return position;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFatherName() {
        return fatherName;
    }
    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }
    public String[][] getPhoneNumbers() {
        return phoneNumbers;
    }
    public String getEmail() {
        return email;
    }
    public String getServiceNumber() {
        return serviceNumber;
    }
    public String getShift() {
        return shift;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Сотрудник: %s %s %s %s\n%11s%s \n" +
                              "%11s%s / %s\n%11sТабельный номер: %s\n" +
                              "%11sСмена: %s\n" +
                              "%11sID: %d\n" +
                              "%11sСтаж работы: ",
                lastName, firstName, fatherName, dayOfBirth,
                "", Arrays.toString(phoneNumbers),
                "", department, position,
                "", serviceNumber,
                "", shift,
                "", id,
                "");
    }
}