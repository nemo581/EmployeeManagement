package com.employee_management.service;

import com.employee_management.model.Position;
import com.employee_management.repository.PositionRepository;
import com.employee_management.repository.impl.JdbcPositionRepository;

import java.util.List;

public class PositionService {
    private static final PositionRepository positionRepository = new JdbcPositionRepository();

    public static List<Position> getAllPositions() {
        return positionRepository.getAllPositions();
    }

    public static List<Position> getAllPositionByDepartmentId(int id) {
        return positionRepository.getAllPositionByDepartmentId(id);
    }
}
