package com.EmployeeManagement;

import com.EmployeeManagement.util.connection.DbConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.EmployeeManagement"})
public class EmployeeManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
		Runtime.getRuntime().addShutdownHook(new Thread(DbConnection::closeConnection));
	}
}