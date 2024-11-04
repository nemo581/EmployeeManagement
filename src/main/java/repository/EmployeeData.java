package repository;

import model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EmployeeData {
    private volatile Connection connection;
    private volatile Statement statement;
    private static volatile List<Employee> employeeList;

    public EmployeeData() {
        readingData();
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    private synchronized Statement getDBConnection() {
        try {
            connection = DriverManager.getConnection(System.getenv("DB_URL"),
                                                     System.getenv("DB_USER"),
                                                     System.getenv("DB_PASSWD"));
            statement = connection.createStatement();
        } catch (SQLException sql) {
            throw new RuntimeException(sql);
        }
        return statement;
    }

    public synchronized void addWorkSchedule(LocalDate date, int mark, int id) {
        java.sql.Date date1 = java.sql.Date.valueOf(date);
        String SQL = "INSERT INTO db_simple.date (date, flag, employee_id) VALUES ('" + date + "', " + mark + ", " + id + ");";
        System.out.println("[ADD NEW ENTRY] " + SQL);
        try {
            getDBConnection().executeUpdate(SQL);
            closeConnection();
            readingData();
        } catch (SQLException sql) {
            System.out.println("[ERROR] СТРОКА 43");
            closeConnection();
            throw new RuntimeException(sql);
        }
    }

    public synchronized void addEmployee(Employee employee) {
        String SQL = "INSERT INTO db_simple.employee (shift, first_name, last_name, patronymic_name) VALUES (" + employee.getShift() + ", " +
                                                                                                                 employee.getFirstName() + ", " +
                                                                                                                 employee.getLustName() + ", " +
                                                                                                                 employee.getFatherName() + ");";
        try {
            getDBConnection().executeUpdate(SQL);
            closeConnection();
            readingData();
        } catch (SQLException sql) {
            closeConnection();
            throw new RuntimeException(sql);
        }
    }

    public synchronized void deleteEmployee(int id) {
        String SQL = "DELETE FROM db_simple.employee WHERE (id = " + id + ")";
        try {
            getDBConnection().executeUpdate(SQL);
            closeConnection();
            readingData();
        } catch (SQLException sql) {
            closeConnection();
            throw new RuntimeException(sql);
        }
    }

    private synchronized void readingData() {
        String SQL = "SELECT * FROM db_simple.employee;";
        employeeList = new CopyOnWriteArrayList<>();
        try (ResultSet resultSet = getDBConnection().executeQuery(SQL)){
            while (resultSet.next()) {
                employeeList.add(new Employee(resultSet.getInt("id"),
                                              resultSet.getString("first_name"),
                                              resultSet.getString("last_name"),
                                              resultSet.getString("patronymic_name"),
                                              resultSet.getInt("shift")));
            }
            closeConnection();
        } catch (SQLException sql) {
            closeConnection();
            throw new RuntimeException(sql);
        }
    }

    private synchronized void closeConnection() {
        try {
            if (this.statement != null) this.statement.close();
            if (this.connection != null) this.connection.close();
        } catch (SQLException sql) {
            throw new RuntimeException(sql);
        }
    }
}