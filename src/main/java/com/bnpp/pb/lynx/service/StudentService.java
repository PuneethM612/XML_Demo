package com.bnpp.pb.lynx.service;

import java.util.List;
import com.bnpp.pb.lynx.model.Student;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentByRollNum(String rollNum);
    void addStudent(Student student);
    boolean studentExists(String rollNum);
} 