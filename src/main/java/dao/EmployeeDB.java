package dao;

import model.Employee;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;

public class EmployeeDB {
    private CopyOnWriteArrayList <Employee> allEmployees;
    private Connection connection;
    private Statement statement;

    public CopyOnWriteArrayList<Employee> getAllEmployees() {
        allEmployees = new CopyOnWriteArrayList<>();
        String SQL = "SELECT drv.id_driver, drv.last_name, drv.first_name, drv.father_name, drv.shift, pos.position_name, dep.department_name " +
                "FROM db_simple.drivers drv " +
                "INNER JOIN db_simple.positions pos " +
                "ON drv.positions_id_positions = pos.id_positions " +
                "INNER JOIN db_simple.department dep " +
                "ON pos.department_id_department = dep.id_department " +
                "ORDER BY CASE WHEN drv.shift=\"2/2\" THEN 1 ELSE 0 END, " +
                "drv.shift ASC, " +
                "drv.last_name ASC;";
        try (ResultSet resultSet = getJDBC().executeQuery(SQL)) {
            while (resultSet.next()) {
                allEmployees.add(new Employee(
                        resultSet.getString("department_name"),
                        resultSet.getString("position_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("father_name"),
                        resultSet.getString("shift"),
                        resultSet.getInt("id_driver")
                        )
                );
            }
        } catch (SQLException sql_err) {
            System.out.println("[15] " + sql_err.getMessage());
        }
        return allEmployees;
    }

    public Employee getEmployeeById(int id) {
        Employee employee = null;
        String SQL = "SELECT drv.id_driver, drv.last_name, drv.first_name, drv.father_name, drv.shift, pos.position_name, dep.department_name " +
                "FROM db_simple.drivers drv " +
                "INNER JOIN db_simple.positions pos " +
                "ON drv.positions_id_positions = pos.id_positions " +
                "INNER JOIN db_simple.department dep " +
                "ON pos.department_id_department = dep.id_department " +
                "WHERE drv.id_driver = " + id + ";";
        try (ResultSet resultSet = getJDBC().executeQuery(SQL)) {
            while (resultSet.next()) {
                employee = new Employee(
                        resultSet.getString("department_name"),
                        resultSet.getString("position_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("father_name"),
                        resultSet.getString("shift"),
                        resultSet.getInt("id_driver")
                );
            }
        } catch (SQLException sql_err) {
            System.out.println("[?]" + sql_err.getMessage());
        }
        return employee;
    }


    public Statement getJDBC() {
        allEmployees = new CopyOnWriteArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                              System.getenv("DB_EMP_URL"),
                              System.getenv("DB_EMP_USER"),
                              System.getenv("DB_EMP_PASSWD"));
            statement = connection.createStatement();
            System.out.println(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException c) {
            c.getException();
        }
        return statement;
    }

    public void closeConnection() {
        try {
            if (this.statement != null) this.statement.close();
            if (this.connection != null) this.connection.close();
        } catch (SQLException sql) {
            throw new RuntimeException(sql);
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        try {
            return  "EmployeeDB{" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) +
                    " Connection is open=" + (!this.connection.isClosed()) +
                    ", Statement is open=" + (!this.statement.isClosed()) +
                    '}';
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
