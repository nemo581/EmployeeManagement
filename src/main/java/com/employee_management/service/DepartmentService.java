package com.employee_management.service;

import com.employee_management.model.Department;
import com.employee_management.repository.DepartmentRepository;
import com.employee_management.repository.impl.JdbcDepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    private static final DepartmentRepository departmentRepository = new JdbcDepartmentRepository();
    public static List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    public static int addDepartment(Department department) {
        return departmentRepository.addDepartment(department.getName());
    }
}
