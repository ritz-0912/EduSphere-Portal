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

     
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String role = request.getParameter("formRole"); 

        User loggedInUser = userDAO.loginUser(user, pass, role);

        if (loggedInUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loggedInUser);
            session.setAttribute("role", loggedInUser.getRole());

         
            if ("ADMIN".equals(loggedInUser.getRole())) {
                response.sendRedirect("admin_dashboard.jsp");
            } else {
                response.sendRedirect("student_dashboard.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid Credentials for the selected role portal!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
