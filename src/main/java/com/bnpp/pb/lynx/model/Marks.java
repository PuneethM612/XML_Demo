package com.bnpp.pb.lynx.model;

public class Marks {
    private String rollNum;
    private int subjectId;
    private String examType;
    private int marks;
    
    // Additional fields for display
    private String studentName;
    private String subjectName;
    
    public Marks() {
    }
    
    public Marks(String rollNum, int subjectId, String examType, int marks) {
        this.rollNum = rollNum;
        this.subjectId = subjectId;
        this.examType = examType;
        this.marks = marks;
    }
    
    public String getRollNum() {
        return rollNum;
    }
    
    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }
    
    public int getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    
    public String getExamType() {
        return examType;
    }
    
    public void setExamType(String examType) {
        this.examType = examType;
    }
    
    public int getMarks() {
        return marks;
    }
    
    public void setMarks(int marks) {
        this.marks = marks;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    @Override
    public String toString() {
        return "Marks{" +
                "rollNum='" + rollNum + '\'' +
                ", subjectId=" + subjectId +
                ", examType='" + examType + '\'' +
                ", marks=" + marks +
                '}';
    }
} 