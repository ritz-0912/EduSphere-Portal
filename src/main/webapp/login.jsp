<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EduSphere | Unified Digital Gateway</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .login-container {
            background: rgba(255, 255, 255, 0.95);
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 440px;
            transition: transform 0.3s ease;
        }

        .portal-header {
            text-align: center;
            margin-bottom: 25px;
        }

        .portal-header h2 {
            color: #1e3c72;
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 6px;
            letter-spacing: -0.5px;
        }

        .portal-header p {
            color: #7f8c8d;
            font-size: 14px;
        }

        /* --- NEW: Interactive Role Selection Blocks --- */
        .role-selection {
            display: flex;
            gap: 12px;
            margin-bottom: 25px;
        }

        .role-box {
            flex: 1;
            border: 2px solid #e2e8f0;
            padding: 12px;
            text-align: center;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 600;
            font-size: 14px;
            color: #64748b;
            transition: all 0.25s ease;
            background: white;
            user-select: none;
        }

        .role-box:hover {
            border-color: #cbd5e1;
            color: #334155;
        }

        .role-box.active {
            border-color: #1e3c72;
            background-color: #f0f4fa;
            color: #1e3c72;
            box-shadow: 0 4px 10px rgba(30, 60, 114, 0.1);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #34495e;
            font-size: 13px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .form-group input {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 15px;
            color: #2d3748;
            outline: none;
            transition: all 0.3s ease;
        }

        .form-group input:focus {
            border-color: #1e3c72;
            box-shadow: 0 0 0 3px rgba(30, 60, 114, 0.15);
        }

        .login-btn {
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
            color: white;
            border: none;
            width: 100%;
            padding: 14px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 8px;
            cursor: pointer;
            box-shadow: 0 5px 15px rgba(30, 60, 114, 0.3);
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .login-btn:hover {
            opacity: 0.95;
            box-shadow: 0 7px 20px rgba(30, 60, 114, 0.4);
        }

        .portal-footer {
            margin-top: 25px;
            text-align: center;
            font-size: 12px;
            color: #94a3b8;
            border-top: 1px solid #e2e8f0;
            padding-top: 15px;
        }

        .error-container {
            background-color: #fee2e2;
            color: #b91c1c;
            padding: 10px 15px;
            border-radius: 6px;
            font-size: 13px;
            margin-bottom: 20px;
            text-align: center;
            border: 1px solid #fca5a5;
            display: none;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <div class="portal-header">
            <h2>🎓 EduSphere</h2>
            <p>Unified Digital Gateway</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error-container" style="display: block;">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <div class="role-selection">
            <div class="role-box active" id="studentTab" onclick="setRole('STUDENT')">🔑 Student</div>
            <div class="role-box" id="adminTab" onclick="setRole('ADMIN')">🛠️ Admin</div>
        </div>

        <form action="LoginServlet" method="POST">
            <input type="hidden" id="selectedRole" name="formRole" value="STUDENT">

            <div class="form-group">
                <label id="usernameLabel" for="username">Student Roll No / Username</label>
                <input type="text" id="username" name="username" required placeholder="Enter roll number">
            </div>

            <div class="form-group">
                <label for="password">Security Password</label>
                <input type="password" id="password" name="password" required placeholder="••••••••">
            </div>

            <button type="submit" class="login-btn">Secure Login →</button>
        </form>

        <div class="portal-footer">
            Student Result Management System &copy; 2026
        </div>
    </div>

    <script>
        function setRole(role) {
            // Update hidden form field
            document.getElementById('selectedRole').value = role;

            // Toggle active classes on tabs
            if(role === 'STUDENT') {
                document.getElementById('studentTab').classList.add('active');
                document.getElementById('adminTab').classList.remove('active');
                document.getElementById('usernameLabel').innerText = 'Student Roll No / Username';
                document.getElementById('username').placeholder = 'Enter roll number';
            } else {
                document.getElementById('adminTab').classList.add('active');
                document.getElementById('studentTab').classList.remove('active');
                document.getElementById('usernameLabel').innerText = 'Administrator ID';
                document.getElementById('username').placeholder = 'Enter admin console user';
            }
        }
    </script>
</body>
</html>