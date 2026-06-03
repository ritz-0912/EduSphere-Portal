package org.example.dao;

import org.example.model.Student;
import org.example.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. Get All Students
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT id, name, roll_no, 'CSE' as branch FROM students";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("roll_no"), rs.getString("branch"));
                studentList.add(student);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { try { if (conn != null && !conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        return studentList;
    }

    // 2. Add Student
    public void addStudent(String name, String rollNo, String branch) {
        Connection conn = DBConnection.getConnection();
        String defaultUsername = name.toLowerCase().replace(" ", "") + "123";
        String query = "INSERT INTO students (username, password, name, roll_no) VALUES (?, 'student123', ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, defaultUsername); ps.setString(2, name); ps.setString(3, rollNo);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        finally { try { if (conn != null && !conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
    }

    // 3. Get Student Result By ID
    public List<String[]> getStudentResult(int studentId) {
        List<String[]> resultList = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT s.subject_name, m.marks_obtained " +
                "FROM marks m JOIN subjects s ON m.subject_id = s.id WHERE m.student_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // ✅ FIX 1: size 2, proper index assignment
                String[] record = new String[2];
                record[0] = rs.getString("subject_name");
                record[1] = String.valueOf(rs.getInt("marks_obtained"));
                resultList.add(record);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { try { if (conn != null && !conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        return resultList;
    }

    // 4. Get Student Result By Username
    public List<String[]> getStudentResultByUsername(String username) {
        List<String[]> resultList = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String cleanedName = username.trim();
        String query = "SELECT s.subject_name, m.marks_obtained " +
                "FROM marks m " +
                "JOIN subjects s ON m.subject_id = s.id " +
                "JOIN students st ON m.student_id = st.id " +
                "WHERE LOWER(REPLACE(st.username, ' ', '')) = LOWER(REPLACE(?, ' ', '')) " +
                "OR LOWER(st.name) = LOWER(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, cleanedName);
            ps.setString(2, cleanedName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // ✅ FIX 2: size 2, proper index assignment
                String[] record = new String[2];
                record[0] = rs.getString("subject_name");
                record[1] = String.valueOf(rs.getInt("marks_obtained"));
                resultList.add(record);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { try { if (conn != null && !conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        return resultList;
    }

    // 5. Add Marks
    public boolean addMarks(int studentId, String subjectName, int marks) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement psSubject = null; PreparedStatement psMarks = null; ResultSet rs = null;
        try {
            String getSubjectIdQuery = "SELECT id FROM subjects WHERE subject_name = ?";
            psSubject = conn.prepareStatement(getSubjectIdQuery); psSubject.setString(1, subjectName);
            rs = psSubject.executeQuery();
            int subjectId = -1;
            if (rs.next()) { subjectId = rs.getInt("id"); } else { return false; }
            String insertMarksQuery = "INSERT INTO marks (student_id, subject_id, marks_obtained) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE marks_obtained = ?";
            psMarks = conn.prepareStatement(insertMarksQuery);
            psMarks.setInt(1, studentId); psMarks.setInt(2, subjectId); psMarks.setInt(3, marks); psMarks.setInt(4, marks);
            return psMarks.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        finally { try { if (rs != null) rs.close(); if (psSubject != null) psSubject.close(); if (psMarks != null) psMarks.close(); if (conn != null && !conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        return false;
    }
}