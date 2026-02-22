package com.employee_management.repository.impl;

import com.employee_management.repository.PositionDepartmentRepository;
import com.employee_management.repository.query.SqlQuery;
import com.employee_management.util.connection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcPositionDepartmentRepository implements PositionDepartmentRepository {
    @Override
    public boolean linkPositionToDepartment(int departmentId, int positionId) {
        String sql = SqlQuery.LINK_POSITION_DEPARTMENT.getQuery();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departmentId);
            statement.setInt(2, positionId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при привязке должности к департаменту: " + e.getMessage(), e);
        }
    }
}