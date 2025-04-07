package com.bnpp.pb.lynx.service;

import java.util.List;

import com.bnpp.pb.lynx.dao.StudentDao;
import com.bnpp.pb.lynx.model.Student;

public class StudentServiceImpl implements StudentService {
    
    private StudentDao studentDao;
    
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public Student getStudentByRollNum(String rollNum) {
        return studentDao.getStudentByRollNum(rollNum);
    }

    @Override
    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    @Override
    public boolean studentExists(String rollNum) {
        return studentDao.studentExists(rollNum);
    }
} 