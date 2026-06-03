package org.example;

import org.example.dao.UserDAO;
import org.example.model.User;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User loggedInUser = userDAO.loginUser("admin", "admin123", "ADMIN");

        if (loggedInUser != null) {
            System.out.println("✅ Login Test Passed!");
            System.out.println("Role: " + loggedInUser.getRole());
            System.out.println("Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            System.out.println("❌ Login Test Failed! Wrong credentials or DB empty.");
        }
    }
}
