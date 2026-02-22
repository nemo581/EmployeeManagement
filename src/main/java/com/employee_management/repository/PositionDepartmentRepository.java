package com.employee_management.repository;

import com.employee_management.util.connection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PositionDepartmentRepository {
    public boolean linkPositionToDepartment(int departmentId, int positionId);
}
