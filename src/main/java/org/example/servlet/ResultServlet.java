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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = user.getUsername();

       
        System.out.println("=== DEBUG ===");
        System.out.println("Session username: " + username);

        List<String[]> result = studentDAO.getStudentResultByUsername(username);

        System.out.println("Result size: " + result.size());
        for (String[] row : result) {
            System.out.println("Subject: " + row[0] + " | Marks: " + row[1]);
        }
        System.out.println("=== END DEBUG ===");

        request.setAttribute("resultList", result);
        request.getRequestDispatcher("student_dashboard.jsp").forward(request, response);
    }
}
