package com.employee_management;

import com.employee_management.service.DepartmentService;
import com.employee_management.service.PositionService;

public class Test_2 {
    public static void main(String[] args) {
        System.out.println(PositionService.getAllPositions());
//        System.out.println(PositionService.getAllPositionByDepartmentId(15));
    }
}
