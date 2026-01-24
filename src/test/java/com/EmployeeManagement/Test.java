package com.EmployeeManagement;

import com.EmployeeManagement.dao.EmployeeRepositoryImpl;
import com.EmployeeManagement.dao.interface_dao.EmployeeRepository;
import com.EmployeeManagement.dao.sql.SqlQuery;
import com.EmployeeManagement.model.Employee;
import com.EmployeeManagement.service.EmployeeService;
import com.EmployeeManagement.util.connection.DbConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
//        Test.readEmployeeTxt();

//        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
//        employeeRepository.findAllEmployee();

        System.out.println("[" + LocalDateTime.now() + "] " + "test");
        EmployeeService employeeService = new EmployeeService();
        List<Employee> list = employeeService.getAllEmployees();




    }
    public static void addEmployeesFromTxt(Employee employee) {
        try(Connection connection = DbConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlQuery.INSERT_MIN_EMPLOYEE_INFO.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getFirst_name());
            statement.setString(2, employee.getLast_name());
            statement.setString(3, employee.getMiddle_name());
            statement.setString(4, employee.getShift());
            System.out.println(statement.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readEmployeeTxt() {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/listEmployee.txt"))) {
            String line;
            int count = 0;
            Employee employee;
            while ((line = reader.readLine()) != null) {
                String[] emp = line.split(" ");
                if (emp.length == 3) {
                    System.out.printf("\u001b[33;1m Reading new Employee :: {%-2d} {array length :: %-1d} {Employee :: %-41s} \u001b[0m\n", (++count), emp.length, Arrays.toString(emp));
                    employee = new Employee(emp[2].trim(), emp[1].trim(), null);
                    employee.setShift(emp[0].trim());
                } else {
                    System.out.printf("\u001b[36;1m  Reading new Employee ::  {%-2d} {array length :: %-1d} {Employee :: %-41s} \u001b[0m\n", (++count), emp.length, Arrays.toString(emp));
                    employee = new Employee(emp[2], emp[1], emp[3]);
                    employee.setShift(emp[0].trim());
                }
                System.out.println("\u001b[35;1m" + "new employee :: " + employee + "\u001b[0m");
                Test.addEmployeesFromTxt(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}