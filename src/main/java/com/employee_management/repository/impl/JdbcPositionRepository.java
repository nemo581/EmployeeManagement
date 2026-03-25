package com.employee_management.repository.impl;

import com.employee_management.model.Position;
import com.employee_management.repository.PositionRepository;
import com.employee_management.repository.query.SqlQuery;
import com.employee_management.util.connection.DbConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPositionRepository implements PositionRepository {
    @Override
    public List<Position> getAllPositions() {
        String sql = SqlQuery.GET_POSITIONS.getQuery();
        List<Position> positions = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             while (resultSet.next()) {
                 Position position = new Position();
                 position.setId(resultSet.getInt("id"));
                 position.setName(resultSet.getString("name"));
                 positions.add(position);
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    @Override
    public List<Position> getAllPositionByDepartmentId(int id) {
        return null;
    }

    @Override
    public int addPosition(String name, BigDecimal salary) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название должности не может быть пустым");
        }
        String sql = SqlQuery.ADD_POSITION.getQuery();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name.trim());
            if (salary == null) {
                ps.setNull(2, Types.DECIMAL);
            } else {
                ps.setBigDecimal(2, salary);
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Не удалось добавить должность");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    System.out.println("Добавлена новая должность: " + name + " (id = " + id + ")");
                    return id;
                } else {
                    throw new SQLException("Не удалось получить ID новой должности");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}