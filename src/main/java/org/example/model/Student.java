package org.example.model;

public class Student {
    private int studentId;
    private String name;
    private String rollNo;
    private String branch;

    // ✅ No-arg constructor (needed when setting fields one by one)
    public Student() {}

    // Parameterized constructor
    public Student(int studentId, String name, String rollNo, String branch) {
        this.studentId = studentId;
        this.name = name;
        this.rollNo = rollNo;
        this.branch = branch;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
}