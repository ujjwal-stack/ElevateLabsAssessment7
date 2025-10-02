package com.employeeDB.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfig {
    private static final String BASE_URL="jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "employee_DB";
    private static final String URL = BASE_URL + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "system";

    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch(ClassNotFoundException e){
            throw new SQLException("Database not found " +e.getMessage());
        }
    }

    /**
     * Initialize database and create table if not exists
     */
    public static void initializeDatabase() {
        String createTableSQL = 
        "CREATE TABLE IF NOT EXISTS employees (" +
        "id INT PRIMARY KEY AUTO_INCREMENT," +
        "first_name VARCHAR(50) NOT NULL," +
        "last_name VARCHAR(50) NOT NULL," +
        "email VARCHAR(100) NOT NULL UNIQUE," +
        "department VARCHAR(50) NOT NULL," +
        "salary DECIMAL(10, 2) NOT NULL," +
        "hire_date DATE NOT NULL," +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
        ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Test database connection
     * @return true if connection successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
