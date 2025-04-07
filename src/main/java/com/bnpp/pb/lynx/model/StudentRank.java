package com.bnpp.pb.lynx.model;

public class StudentRank {
    private String rollNum;
    private String name;
    private int totalMarks;
    private int rank;
    
    public StudentRank() {
    }
    
    public StudentRank(String rollNum, String name, int totalMarks, int rank) {
        this.rollNum = rollNum;
        this.name = name;
        this.totalMarks = totalMarks;
        this.rank = rank;
    }
    
    public String getRollNum() {
        return rollNum;
    }
    
    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getTotalMarks() {
        return totalMarks;
    }
    
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
} 