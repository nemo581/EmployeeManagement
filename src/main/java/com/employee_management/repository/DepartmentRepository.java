package com.employee_management.repository;

import com.employee_management.model.Department;

import java.util.List;

public interface DepartmentRepository {
    public List<Department> getAllDepartments();
    public int addDepartment(String name);
}
