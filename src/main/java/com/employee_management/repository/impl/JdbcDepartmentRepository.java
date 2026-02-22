package com.employee_management.repository.impl;

import com.employee_management.repository.DepartmentRepository;
import com.employee_management.repository.query.SqlQuery;
import com.employee_management.model.Department;
import com.employee_management.util.connection.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDepartmentRepository implements DepartmentRepository {
    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = SqlQuery.GET_DEPARTMENTS.getQuery();
        Connection connection = DbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                departments.add(new Department(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
        return departments;
    }

    @Override
    public int addDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название департамента не может быть пустым");
        }
        String sql = SqlQuery.ADD_DEPARTMENT.getQuery();
        Connection connection = DbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name.trim());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.close();
                throw new SQLException("Не удалось добавить департамент");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    System.out.println("Добавлен департамент: " + name + " (id = " + id + ")");
                    return id;
                } else {
                    connection.close();
                    throw new SQLException("Не удалось получить ID нового департамента");
                }
            }
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}