package org.example.dao;

import org.example.util.DBConnection;
import org.example.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Login logic updated to handle role-based multi-table authentication
    public User loginUser(String username, String password, String role) {
        Connection conn = DBConnection.getConnection();
        String query = "";

        // JSP login toggle screen selector switch logical check
        if ("ADMIN".equals(role)) {
            query = "SELECT id, username, 'ADMIN' as role FROM admins WHERE username = ? AND password = ?";
        } else {
            query = "SELECT id, username, 'STUDENT' as role FROM students WHERE username = ? AND password = ?";
        }

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Return User object with perfectly sequential clean mapping IDs
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connection safely if needed based on your DB pool architecture
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}