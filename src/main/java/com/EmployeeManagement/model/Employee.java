package com.EmployeeManagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {
    private Integer employee_id;
    private String tab_number;
    private String shift;
    private String first_name;
    private String last_name;
    private String middle_name;
    private LocalDate birth_date;
    private String photo_path;
    private LocalDate hire_date;
    private LocalDateTime create_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;
    private Boolean is_active;

    public Employee() {
    }

    public Employee(String first_name, String last_name, String middle_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getTab_number() {
        return tab_number;
    }

    public void setTab_number(String tab_number) {
        this.tab_number = tab_number;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public LocalDate getHire_date() {
        return hire_date;
    }

    public void setHire_date(LocalDate hire_date) {
        this.hire_date = hire_date;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "\u001B[36m" + "Employee{" +
                "employee_id='" + employee_id + '\'' +
                ", tab_number='" + tab_number + '\'' +
                ", shift='" + shift + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", is_active='" + is_active + '\'' +
                '}' + "\u001B[0m";
    }
}