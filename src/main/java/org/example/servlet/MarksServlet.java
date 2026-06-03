package org.example.servlet;

import org.example.dao.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/MarksServlet")
public class MarksServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Frontend form se input values nikalna
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String subjectName = request.getParameter("subjectName");
            int marks = Integer.parseInt(request.getParameter("marks"));

            // 2. StudentDAO ki method call karke data database mein save karna
            studentDAO.addMarks(studentId, subjectName, marks);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 3. Marks save hone ke baad wapas admin dashboard ko reload karna data refresh ke sath
        response.sendRedirect("StudentServlet");
    }
}