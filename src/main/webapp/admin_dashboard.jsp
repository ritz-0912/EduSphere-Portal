<%

    if (session.getAttribute("user") == null || !"ADMIN".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }


    if (request.getAttribute("students") == null) {
        request.getRequestDispatcher("StudentServlet").forward(request, response);
        return;
    }
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Student" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Portal | Control Center</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #f4f6f9; color: #333; display: flex; flex-direction: column; min-height: 100vh; }
        header { background: #2c3e50; color: #fff; padding: 20px 40px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        header h1 { font-size: 24px; font-weight: 600; }
        .logout-btn { background: #e74c3c; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; font-weight: bold; transition: 0.3s; }
        .logout-btn:hover { background: #c0392b; }
        .container { display: flex; gap: 30px; padding: 40px; flex-wrap: wrap; }
        .panel { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); flex: 1; min-width: 350px; }
        .panel h3 { margin-bottom: 20px; color: #2c3e50; font-size: 18px; border-bottom: 2px solid #ecf0f1; padding-bottom: 8px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-size: 14px; font-weight: 500; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px; }
        .submit-btn { background: #3498db; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; width: 100%; font-size: 15px; font-weight: bold; transition: 0.3s; }
        .submit-btn:hover { background: #2980b9; }
        .table-panel { flex: 2; min-width: 500px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; background: white; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #34495e; color: white; font-weight: 600; }
        tr:hover { background-color: #f9f9f9; }
        .refresh-btn { background: #2ecc71; color: white; padding: 10px 20px; border: none; border-radius: 4px; text-decoration: none; font-weight: bold; display: inline-block; margin-top: 15px; }
        .refresh-btn:hover { background: #27ae60; }
    </style>
</head>
<body>

    <header>
        <h1>🎓 Student Management System | Admin Portal</h1>
        <a href="login.jsp" class="logout-btn">Logout</a>
    </header>

    <div class="container">
        <div style="display: flex; flex-direction: column; gap: 30px; flex: 1;">
            <div class="panel">
                <h3>+ Register New Student</h3>
                <form action="StudentServlet" method="POST">
                    <div class="form-group">
                        <label>Student Name</label>
                        <input type="text" name="name" required placeholder="John Doe">
                    </div>
                    <div class="form-group">
                        <label>Roll Number</label>
                        <input type="text" name="rollNo" required placeholder="2026CS101">
                    </div>
                    <div class="form-group">
                        <label>Branch / Department</label>
                        <input type="text" name="branch" required placeholder="Computer Science">
                    </div>
                    <button type="submit" class="submit-btn">Add Student Records</button>
                </form>
            </div>

            <div class="panel">
                <h3>📝 Term End Marks Entry</h3>
                <form action="MarksServlet" method="POST">
                    <div class="form-group">
                        <label>Student ID</label>
                        <input type="number" name="studentId" required placeholder="Ex: 1">
                    </div>
                    <div class="form-group">
                        <label>Subject Module</label>
                        <input type="text" name="subjectName" required placeholder="Ex: Advanced Java">
                    </div>
                    <div class="form-group">
                        <label>Marks Obtained</label>
                        <input type="number" name="marks" min="0" max="100" required placeholder="0 - 100">
                    </div>
                    <button type="submit" class="submit-btn" style="background: #e67e22;">Publish Marks</button>
                </form>
            </div>
        </div>

        <div class="panel table-panel">
            <h3>📋 System Database Records</h3>
            <table>
                <thead>
                    <tr>
                        <th>System ID</th>
                        <th>Full Name</th>
                        <th>Academic Roll No</th>
                        <th>Branch</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Student> list = (List<Student>) request.getAttribute("students");
                        if (list != null && !list.isEmpty()) {
                            for (Student s : list) {
                    %>
                            <tr>
                                <td><strong>#<%= s.getStudentId() %></strong></td>
                                <td><%= s.getName() %></td>
                                <td><%= s.getRollNo() %></td>
                                <td><%= s.getBranch() %></td>
                            </tr>
                    <%
                            }
                        } else {
                    %>
                            <tr>
                                <td colspan="4" style="text-align: center; color: #7f8c8d; padding: 30px;">
                                    No records loaded from database.
                                </td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <a href="StudentServlet" class="refresh-btn">🔄 Fetch / Sync Live Records</a>
        </div>
    </div>

</body>
</html>
