package com.EmployeeManagement.dao;

import com.EmployeeManagement.dao.interface_dao.EmployeeRepository;
import com.EmployeeManagement.dao.sql.SqlQuery;
import com.EmployeeManagement.model.Employee;
import com.EmployeeManagement.util.connection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public List<Employee> findAllEmployee() {
        List<Employee> employee_list = new ArrayList<>();
        String sql = SqlQuery.GET_ALL_EMPLOYEES.getQuery();
        Connection connection = DbConnection.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_name")
                );
                employee.setEmployee_id(rs.getInt("employee_id"));

                String shift = rs.getString("shift");
                employee.setShift((shift != null && !shift.isEmpty()) ? shift : null);

                String tub_number = rs.getString("tab_number");
                employee.setTab_number((tub_number != null && !tub_number.isEmpty()) ? tub_number : null);

                System.out.println(employee);
                employee_list.add(employee);
            }
            connection.close();
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
        return employee_list;
    }

    @Override
    public Employee findEmployeeById(int id) {
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {
    }
}