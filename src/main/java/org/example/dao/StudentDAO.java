package org.example.dao;

import org.example.util.DBConnection;
import org.example.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. Admin Feature: Naya Student Add Karne ki Method
    public boolean addStudent(String name, String rollNo, String branch) {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO students (name, roll_no, branch) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, rollNo);
            ps.setString(3, branch);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Admin Feature: Sabhi Students ki List Fetch Karne ki Method
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM students";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("branch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Admin Feature: Student ke Marks aur Subject Enter/Save Karne ki Method
    public boolean addMarks(int studentId, String subjectName, int marksObtained) {
        Connection conn = DBConnection.getConnection();
        try {
            int subjectId = -1;
            // Check karte hain agar subject pehle se hai
            String subCheckQuery = "SELECT subject_id FROM subjects WHERE subject_name = ?";
            PreparedStatement ps1 = conn.prepareStatement(subCheckQuery);
            ps1.setString(1, subjectName);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                subjectId = rs.getInt("subject_id");
            } else {
                // Agar naya subject hai toh insert karo
                String subInsertQuery = "INSERT INTO subjects (subject_name) VALUES (?)";
                PreparedStatement ps2 = conn.prepareStatement(subInsertQuery, Statement.RETURN_GENERATED_KEYS);
                ps2.setString(1, subjectName);
                ps2.executeUpdate();
                ResultSet rsKeys = ps2.getGeneratedKeys();
                if (rsKeys.next()) subjectId = rsKeys.getInt(1);
            }

            // Marks table mein data insert karna
            String marksQuery = "INSERT INTO marks (student_id, subject_id, marks_obtained) VALUES (?, ?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(marksQuery);
            ps3.setInt(1, studentId);
            ps3.setInt(2, subjectId);
            ps3.setInt(3, marksObtained);

            return ps3.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Student Feature: Kisi Ek Student Ke Saare Marks Fetch Karne Ki Method (Join Query)
    public List<String[]> getStudentResult(int userId) {
        List<String[]> resultList = new ArrayList<>();
        Connection conn = DBConnection.getConnection();

        String query = "SELECT sub.subject_name, m.marks_obtained FROM marks m " +
                "JOIN subjects sub ON m.subject_id = sub.subject_id " +
                "JOIN students s ON m.student_id = s.student_id " +
                "WHERE s.user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(new String[]{rs.getString("subject_name"), rs.getString("marks_obtained")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}