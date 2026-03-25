package com.employee_management.repository;

import com.employee_management.model.Position;

import java.math.BigDecimal;
import java.util.List;

public interface PositionRepository {
    public List<Position> getAllPositions();
    public List<Position> getAllPositionByDepartmentId(int id);
    public int addPosition(String name, BigDecimal salary);
}
