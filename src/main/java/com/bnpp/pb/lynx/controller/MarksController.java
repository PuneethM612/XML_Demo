package com.bnpp.pb.lynx.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bnpp.pb.lynx.model.Marks;
import com.bnpp.pb.lynx.model.Student;
import com.bnpp.pb.lynx.service.MarksService;
import com.bnpp.pb.lynx.service.StudentService;
import com.bnpp.pb.lynx.service.SubjectService;

public class MarksController {
    
    private MarksService marksService;
    private StudentService studentService;
    private SubjectService subjectService;
    
    public void setMarksService(MarksService marksService) {
        this.marksService = marksService;
    }
    
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    @GetMapping("/marks")
    public String marksForm(Model model) {
        model.addAttribute("examTypes", new String[]{"monthly", "mid", "annual"});
        return "marks_search";
    }
    
    @GetMapping("/marks/search")
    public String getMarksByRollNumAndExamType(
            @RequestParam("rollNum") String rollNum,
            @RequestParam("examType") String examType,
            Model model) {
        
        Student student = studentService.getStudentByRollNum(rollNum);
        
        if (student == null) {
            model.addAttribute("error", "No student found with Roll Number: " + rollNum);
            model.addAttribute("examTypes", new String[]{"monthly", "mid", "annual"});
            return "marks_search";
        }
        
        List<Marks> marksList = marksService.getMarksByRollNumAndExamType(rollNum, examType);
        
        model.addAttribute("student", student);
        model.addAttribute("marksList", marksList);
        model.addAttribute("examType", examType);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("newMarks", new Marks());
        
        return "marks_view";
    }
    
    @PostMapping("/marks/update")
    public String updateMarks(@ModelAttribute Marks marks, Model model) {
        marksService.saveOrUpdateMarks(marks);
        return "redirect:/marks/search?rollNum=" + marks.getRollNum() + "&examType=" + marks.getExamType();
    }
} 