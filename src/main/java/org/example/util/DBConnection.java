package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root"; // Apna MySQL username check kar lena
    private static final String PASSWORD = "1234"; // Yahan Workbench ka password daalo
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🔥 Database Connected Successfully!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("❌ Connection Failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}