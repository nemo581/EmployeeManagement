package com.EmployeeManagement.util.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static Properties properties;
    private static HikariDataSource dataSource;

    public static Connection getConnection() {
        if (dataSource == null) {
            if (properties == null) properties = loadProperties();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("DB_URL"));
            config.setUsername(properties.getProperty("DB_USER"));
            config.setPassword(properties.getProperty("DB_PASSWORD"));
            dataSource = new HikariDataSource(config);
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить соединение из пула", e);
        }
    }

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DbConnection.class.getResourceAsStream("/simple_db.env")) {
            if (inputStream == null) {
                throw new RuntimeException("simple_db.env не найден в classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать конфигурацию...", e);
        }
        return properties;
    }

    public static void closeConnection() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("Пул соединений закрыт");
        }
    }
}