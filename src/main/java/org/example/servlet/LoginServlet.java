package org.example.servlet;

import org.example.dao.UserDAO;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Form se teenon parameters ko fetch karenge
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String role = request.getParameter("formRole"); // Humare JSP interface se STUDENT ya ADMIN milega

        // 2. UserDAO ko teenon variables pass karenge validation ke liye
        User loggedInUser = userDAO.loginUser(user, pass, role);

        if (loggedInUser != null) {
            HttpSession session = request.getSession();
            // Pura User object session mein daalenge (jiski id ab 1, 2, 3 ordered hogi)
            session.setAttribute("user", loggedInUser);
            session.setAttribute("role", loggedInUser.getRole());

            // 3. Conditional routing for dashboard panels
            if ("ADMIN".equals(loggedInUser.getRole())) {
                response.sendRedirect("admin_dashboard.jsp");
            } else {
                response.sendRedirect("student_dashboard.jsp");
            }
        } else {
            // Humare naye redesigned login interface ke modern error box ko string target pass karenge
            request.setAttribute("error", "Invalid Credentials for the selected role portal!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}