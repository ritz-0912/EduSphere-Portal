package org.example.servlet;

import org.example.dao.StudentDAO;
import org.example.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    // 1. GET Request: Jab Admin "Fetch/Sync Live Records" click karega ya page refresh hoga
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Database se saare students lekar aana
        List<Student> studentList = studentDAO.getAllStudents();

        // List ko request ke andar set karna taaki JSP page ise access kar sake
        request.setAttribute("students", studentList);

        // Wapas admin dashboard par bhej dena data ke sath
        request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
    }

    // 2. POST Request: Jab Admin form bhar kar "Add Student Records" par click karega
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Frontend input fields se data nikalna
        String name = request.getParameter("name");
        String rollNo = request.getParameter("rollNo");
        String branch = request.getParameter("branch");

        // Database mein data insert karna via StudentDAO
        studentDAO.addStudent(name, rollNo, branch);

        // Data insert hone ke baad, list ko automatic refresh karne ke liye doGet ko call karna
        response.sendRedirect("StudentServlet");
    }
}