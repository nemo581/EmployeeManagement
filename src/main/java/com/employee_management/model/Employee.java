package com.employee_management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private Integer employeeId;
    private String tabNumber;
    private Department department;
    private Position position;
    private String shift;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String photoPath;
    private final List<Email> email = new ArrayList<>();
    private final List<Phone> phone = new ArrayList<>();
    private LocalDate hireDate;
    private LocalDateTime terminationDate;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean isActive;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        if (middleName == null || middleName.isEmpty()) {
            return lastName + " " + firstName;
        } else {
            return lastName + " " + firstName + " " + middleName;
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDepartment() {
        return department.getName();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPosition() {
        return position.getName();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Email> getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email.add(email);
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone.add(phone);
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDateTime getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDateTime terminationDate) {
        this.terminationDate = terminationDate;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "\u001B[36m" + "Employee{" +
                "employee_id='" + employeeId + '\'' +
                ", tab_number='" + tabNumber + '\'' +
                ", shift='" + shift + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", middle_name='" + middleName + '\'' +
                ", birth_date='" + birthDate + '\'' +
                ", is_active='" + isActive + '\'' +
                '}' + "\u001B[0m";
    }
}