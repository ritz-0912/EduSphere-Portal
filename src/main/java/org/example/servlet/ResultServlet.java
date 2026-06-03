package org.example.servlet;

import org.example.dao.StudentDAO;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // User ID ke basis par result lekar aana
            List<String[]> result = studentDAO.getStudentResult(user.getId());
            request.setAttribute("resultList", result);
            request.getRequestDispatcher("student_dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}