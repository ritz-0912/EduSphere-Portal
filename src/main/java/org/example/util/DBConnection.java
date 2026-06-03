package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ DB Connected!");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Connection Failed!");
            e.printStackTrace();
            throw new RuntimeException("DB Connection failed!");
        }
    }
}
