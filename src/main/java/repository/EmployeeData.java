package repository;

import model.Employee;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {
    private final List<Employee> employeeList = new ArrayList<>();
    private final String url;

    public EmployeeData(String url) {
        this.url = url;
        readingData();
    }

    private void readingData() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(url), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (line != null) {
            String[] arr = line.split(" ");
            if (arr.length == 4) {
                employeeList.add(new Employee(arr[1], arr[2], arr[3], Integer.parseInt(arr[0])));
            } else if (arr.length == 3) {
                employeeList.add(new Employee(arr[1], arr[2], null, Integer.parseInt(arr[0])));
            }
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
}