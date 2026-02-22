package com.employee_management;

import com.employee_management.repository.query.SqlQuery;
import com.employee_management.model.Email;
import com.employee_management.model.Employee;
import com.employee_management.util.connection.DbConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("[" + LocalDateTime.now() + "] " + "test");
//        List<Employee> employees = EmployeeService.getAllEmployees();
        readEmployeeTxt();

//        String placeholders = String.join(",", Collections.nCopies(employees.size(), "?"));
        String sql = """
                SELECT\s
                        employee_id,
                        email,
                        type,
                        is_main,
                        is_active
                    FROM employee_email""";

        Map<Integer, List<Email>> emailsByEmp = new HashMap<>();

        Connection connection = DbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                System.out.println(">>> " + rs.getInt("employee_id") + " " +
                        rs.getString("email") + " " +
                        rs.getString("type") + " " +
                        rs.getBoolean("is_main") + " " +
                        rs.getBoolean("is_active"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("error", e);
        }


//        LocalDate date = LocalDate.of(2026, 2, 1);
//        System.out.println(date.lengthOfMonth());
//        int d_length = date.lengthOfMonth();
//
//        String[][] days = new String[date.lengthOfMonth()][2];
//
//        for (int i = 0; i < days.length; i++) {
//            days[i][0] = String.valueOf(i+1);
//            days[i][1] = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru", "Ru"));
//            date = date.plusDays(1);
//        }
//
//        for (String[] day : days) {
//            System.out.println(day[0] + "::" + day[1]);
//        }
//        for (int i = 0; i < date.lengthOfMonth(); i++) {
//            days[i][0] = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("ru", "Ru"));
//            date = date.plusDays(1);
//        }


    }

    public static void addEmployeesFromTxt(Employee employee) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.INSERT_MIN_EMPLOYEE_INFO.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getMiddleName());
            statement.setString(4, employee.getShift());
            System.out.println(statement.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readEmployeeTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/listEmployee.txt"))) {
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