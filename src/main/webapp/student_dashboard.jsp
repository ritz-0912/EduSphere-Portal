<%
    if (session.getAttribute("user") == null || !"STUDENT".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Portal | Report Card</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #f5f7fa; color: #333; }
        header { background: #2c3e50; color: #fff; padding: 20px 40px; display: flex; justify-content: space-between; align-items: center; }
        .logout-btn { background: #e74c3c; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; font-weight: bold; }
        .container { max-width: 800px; margin: 50px auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        .welcome-msg { margin-bottom: 30px; font-size: 22px; color: #2c3e50; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #e1e8ed; }
        th { background-color: #34495e; color: white; }
        .summary-row { font-weight: bold; background-color: #f8f9fa; }
        .grade-badge { padding: 5px 12px; border-radius: 20px; font-weight: bold; font-size: 14px; background: #2ecc71; color: white; display: inline-block; }
        .action-btn { background: #3498db; color: white; padding: 12px 24px; border: none; border-radius: 4px; text-decoration: none; font-weight: bold; display: inline-block; margin-top: 20px; text-align: center; cursor: pointer;}
    </style>
</head>
<body>

    <%
        User user = (User) session.getAttribute("user");
        String username = (user != null) ? user.getUsername() : "Student Account";
    %>

    <header>
        <h1>🎓 Student Self-Service Portal</h1>
        <a href="login.jsp" class="logout-btn">Logout</a>
    </header>

    <div class="container">
        <div class="welcome-msg">Welcome Back, <strong><%= username %></strong>! 👋</div>
        <h3 style="color: #7f8c8d; margin-bottom: 15px;">Official Academic Performance Statement</h3>

        <table>
            <thead>
                <tr>
                    <th>Academic Subject Course</th>
                    <th>Marks (Out of 100)</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<String[]> results = (List<String[]>) request.getAttribute("resultList");
                    int totalMarks = 0;
                    int subjectCount = 0;

                    if (results != null && !results.isEmpty()) {
                        for (String[] row : results) {
                            // FIXED: Explicit array index for marks
                            int marks = Integer.parseInt(row[1]);
                            totalMarks += marks;
                            subjectCount++;
                %>
                        <tr>
                            <td><strong><%= row[0] %></strong></td>
                            <td><%= marks %></td>
                        </tr>
                <%
                        }

                        double percentage = 0.0;
                        String grade = "F";
                        if (subjectCount > 0) {
                            percentage = (double) totalMarks / subjectCount;
                            if (percentage >= 90) grade = "A+";
                            else if (percentage >= 80) grade = "A";
                            else if (percentage >= 70) grade = "B";
                            else if (percentage >= 50) grade = "C";
                        }
                %>
                    <tr class="summary-row">
                        <td>Aggregate Total Score:</td>
                        <td><%= totalMarks %> / <%= (subjectCount * 100) %></td>
                    </tr>
                    <tr class="summary-row">
                        <td>Calculated Percentage:</td>
                        <td><%= String.format("%.2f", percentage) %>%</td>
                    </tr>
                    <tr class="summary-row" style="background: #e8f8f5;">
                        <td style="color: #16a085;">Final Performance Classification:</td>
                        <td><span class="grade-badge"><%= grade %></span></td>
                    </tr>
                <%
                    } else {
                %>
                    <tr>
                        <td colspan="2" style="text-align: center; color: #95a5a6; padding: 40px;">
                            No examination records published for this account yet.
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <a href="ResultServlet" class="action-btn">🔄 Generate & Pull Report Card</a>
    </div>

</body>
</html>