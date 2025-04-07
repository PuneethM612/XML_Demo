package com.bnpp.pb.lynx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bnpp.pb.lynx.model.Student;
import com.bnpp.pb.lynx.service.StudentService;

public class StudentController {
    
    private StudentService studentService;
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @GetMapping("/")
    public String index() {
        return "redirect:/marks";
    }
    
    @GetMapping("/students")
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student());
        return "students";
    }
    
    @PostMapping("/students")
    public String addStudent(@ModelAttribute Student student, Model model) {
        if (!studentService.studentExists(student.getRollNum())) {
            studentService.addStudent(student);
            return "redirect:/students";
        }
        
        model.addAttribute("error", "Student with Roll Number " + student.getRollNum() + " already exists!");
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }
} 