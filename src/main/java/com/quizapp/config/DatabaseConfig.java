package com.quizapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3307/quiz_game_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private static final int CONNECTION_TIMEOUT = 5;
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                    DB_URL + "?useSSL=false&serverTimezone=UTC",
                    DB_USER,
                    DB_PASSWORD
                );
                System.out.println("✓ New database connection established");
            }
            
            return connection;
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found! Add mysql-connector-java to classpath", e);
        }
    }
    
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("✗ Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error closing connection: " + e.getMessage());
        }
    }
    
    public static boolean checkDatabaseHealth() {
        try {
            Connection conn = getConnection();
            return conn.createStatement().execute("SELECT 1");
        } catch (SQLException e) {
            return false;
        }
    }
}