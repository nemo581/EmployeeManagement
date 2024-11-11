package repository;

import model.Employee;
import service.WorkSchedule;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EmployeeData {
    private volatile Connection connection;
    private volatile Statement statement;
    private static volatile List<Employee> employeeList;

    public synchronized List<Employee> getAllEmployees() {
        employeeList = new CopyOnWriteArrayList<>();
        String SQL = "SELECT * FROM db_simple.employee;";
        try (ResultSet resultSet = getDBConnection().executeQuery(SQL)) {
            while (resultSet.next()) {
                Employee employee = new Employee(resultSet.getInt("id"),
                                                 resultSet.getString("first_name"),
                                                 resultSet.getString("last_name"),
                                                 resultSet.getString("patronymic_name"),
                                                 resultSet.getInt("shift"));
                employeeList.add(employee);
            }
            closeConnection();
        } catch (SQLException sql) {
            closeConnection();
            throw new RuntimeException(sql);
        }
        return employeeList;
    }

    public synchronized Employee getEmployeeById(int id) {
        Employee employee = null;
        String SQL = "select * from db_simple.employee where id = " + id + ";";
        try (ResultSet resultSet = getDBConnection().executeQuery(SQL)){
            while (resultSet.next()) {
                employee = new Employee(resultSet.getInt("id"),
                                        resultSet.getString("first_name"),
                                        resultSet.getString("last_name"),
                                        resultSet.getString("patronymic_name"),
                                        resultSet.getInt("shift"));
            }
            closeConnection();
        } catch (SQLException sql) {
            closeConnection();
            throw new RuntimeException(sql);
        }
        return employee;
    }

    public synchronized List<Employee> getAllWorkSchedule() {
        employeeList = getAllEmployees();
        for (Employee emp : employeeList) {
            String SQL = "select * from db_simple.date  \n" +
                         "where employee_id = " + emp.getId() + " and date \n" +
                         "between '2024-11-01' and '2024-11-30' \n" +
                         "order by date;";
            try (ResultSet resultSet = getDBConnection().executeQuery(SQL)) {
                while (resultSet.next()) {
                    emp.setEmployeeWorkDays(resultSet.getDate("date").toLocalDate(),
                                            resultSet.getInt("flag"));
                }
            } catch (SQLException sql) {
                closeConnection();
                throw new RuntimeException(sql);
            }
        }
        return employeeList;
    }

    public synchronized Employee getWorkScheduleById(int id) {
        Employee employee = getEmployeeById(id);
        String SQL = "SELECT * FROM db_simple.date where employee_id = " + id + " and date between '2024-11-01' and '2024-11-30' order by date;";
        try (ResultSet resultSet = getDBConnection().executeQuery(SQL)) {
            while (resultSet.next()) {
                employee.setEmployeeWorkDays(resultSet.getDate("date").toLocalDate(), resultSet.getInt("flag"));
            }
            closeConnection();
        } catch (SQLException sql) {
            System.out.println("[ERROR] СТРОКА 65");
            closeConnection();
            throw new RuntimeException(sql);
        }
        return employee;
    }

    public synchronized void addNewEmployee(Employee employee) {
        String SQL = "INSERT INTO db_simple.employee (shift, first_name, last_name, patronymic_name) VALUES (" + employee.getShift() + ", " +
                employee.getFirstName() + ", " +
                employee.getLustName() + ", " +
                employee.getPatronymicName() + ");";
        try {
            getDBConnection().executeUpdate(SQL);
            closeConnection();
        } catch (SQLException sql) {
            closeConnection();
            throw new RuntimeException(sql);
        }
    }

    public synchronized void deleteEmployee(int id) {
    }

    public synchronized void fillInTheSchedule(LocalDate date, int mark, int employeeId) {
        String SQL = "INSERT INTO db_simple.date (date, flag, employee_id) VALUES ('" + date + "', " + mark + ", " + employeeId + ");";
        System.out.println("[ADD NEW ENTRY] " + SQL);
        try {
            getDBConnection().executeUpdate(SQL);
            closeConnection();
        } catch (SQLException sql) {
            System.out.println("[ERROR] СТРОКА 76");
            closeConnection();
            throw new RuntimeException(sql);
        }
    }

    public void updateWorkSchedule() {
        int year = LocalDate.now().getYear() + 1;
        WorkSchedule workSchedule = new WorkSchedule(LocalDate.of(year, 1, 1));
        workSchedule.getWorkSchedule(this);
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

    private synchronized void closeConnection() {
        try {
            if (this.statement != null) this.statement.close();
            if (this.connection != null) this.connection.close();
        } catch (SQLException sql) {
            throw new RuntimeException(sql);
        }
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
}