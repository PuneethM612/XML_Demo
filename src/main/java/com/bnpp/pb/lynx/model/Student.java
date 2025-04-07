package com.bnpp.pb.lynx.model;

public class Student {
    private String name;
    private String rollNum;
    
    public Student() {
    }
    
    public Student(String name, String rollNum) {
        this.name = name;
        this.rollNum = rollNum;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRollNum() {
        return rollNum;
    }
    
    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNum='" + rollNum + '\'' +
                '}';
    }
} 