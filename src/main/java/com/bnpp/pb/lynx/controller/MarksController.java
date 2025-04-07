package com.bnpp.pb.lynx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bnpp.pb.lynx.model.Marks;
import com.bnpp.pb.lynx.model.Student;
import com.bnpp.pb.lynx.model.Subject;
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
    
    @GetMapping("/marks/add-multiple")
    public String addMultipleMarksForm(
            @RequestParam("rollNum") String rollNum,
            @RequestParam("examType") String examType,
            Model model) {
        
        Student student = studentService.getStudentByRollNum(rollNum);
        
        if (student == null) {
            model.addAttribute("error", "No student found with Roll Number: " + rollNum);
            model.addAttribute("examTypes", new String[]{"monthly", "mid", "annual"});
            return "marks_search";
        }
        
        // Get all subjects
        List<Subject> subjects = subjectService.getAllSubjects();
        
        // Get existing marks for this student and exam
        List<Marks> existingMarks = marksService.getMarksByRollNumAndExamType(rollNum, examType);
        
        // Create a map of subjectId -> marks value for easier access in the template
        Map<Integer, Integer> marksMap = new HashMap<>();
        for (Marks mark : existingMarks) {
            marksMap.put(mark.getSubjectId(), mark.getMarks());
        }
        
        model.addAttribute("student", student);
        model.addAttribute("examType", examType);
        model.addAttribute("subjects", subjects);
        model.addAttribute("marksMap", marksMap);
        
        return "marks_add_multiple";
    }
    
    @PostMapping("/marks/save-multiple")
    public String saveMultipleMarks(
            @RequestParam("rollNum") String rollNum,
            @RequestParam("examType") String examType,
            @RequestParam("subjectIds[]") int[] subjectIds,
            @RequestParam("marks[]") int[] marksValues,
            Model model) {
        
        // Save marks for each subject
        for (int i = 0; i < subjectIds.length; i++) {
            Marks marks = new Marks();
            marks.setRollNum(rollNum);
            marks.setExamType(examType);
            marks.setSubjectId(subjectIds[i]);
            marks.setMarks(marksValues[i]);
            
            marksService.saveOrUpdateMarks(marks);
        }
        
        return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType;
    }
} 