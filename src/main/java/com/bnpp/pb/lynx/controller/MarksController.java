package com.bnpp.pb.lynx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
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

@Controller
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
    public String updateMarks(
            @RequestParam("rollNum") String rollNum,
            @RequestParam("examType") String examType,
            @RequestParam(value = "subjectId", required = true) Integer subjectId,
            @RequestParam(value = "marks", required = true) Integer marks) {
        
        System.out.println("DEBUG: Updating marks for rollNum=" + rollNum + ", examType=" + examType + 
                          ", subjectId=" + subjectId + ", marks=" + marks);
        
        try {
            if (rollNum == null || examType == null || subjectId == null || marks == null) {
                System.out.println("ERROR: Required parameters are missing");
                return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType + "&error=missing_params";
            }
            
            Marks marksObj = new Marks();
            marksObj.setRollNum(rollNum);
            marksObj.setExamType(examType);
            marksObj.setSubjectId(subjectId);
            marksObj.setMarks(marks);
            
            marksService.saveOrUpdateMarks(marksObj);
            return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType + "&success=true";
        } catch (Exception e) {
            System.out.println("ERROR: Exception occurred while updating marks: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType + "&error=" + e.getMessage();
        }
    }
    
    @GetMapping("/marks/add-multiple")
    public String addMultipleMarksForm(
            @RequestParam("rollNum") String rollNum,
            @RequestParam("examType") String examType,
            Model model) {
        
        System.out.println("DEBUG: Entering addMultipleMarksForm method with rollNum=" + rollNum + ", examType=" + examType);
        
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
        
        System.out.println("DEBUG: Returning template name: 'marks_form'");
        // Use a simpler template name to avoid any issues with underscores
        return "marks_form";
    }
    
    @PostMapping("/marks/save-multiple")
    public String saveMultipleMarks(
            @RequestParam("rollNum") String rollNum,
            @RequestParam("examType") String examType,
            @RequestParam Map<String, String> params) {
        
        System.out.println("DEBUG: Received save-multiple request with rollNum=" + rollNum + ", examType=" + examType);
        System.out.println("DEBUG: All parameters: " + params);
        
        try {
            List<Integer> subjectIds = new ArrayList<>();
            List<Integer> marksValues = new ArrayList<>();
            
            // Extract subject IDs and marks from the request parameters
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                
                if (key.startsWith("subjectIds[") && key.endsWith("]")) {
                    if (value != null && !value.trim().isEmpty()) {
                        int subjectId = Integer.parseInt(value);
                        subjectIds.add(subjectId);
                        System.out.println("DEBUG: Added subject ID: " + subjectId);
                    }
                } else if (key.startsWith("marks[") && key.endsWith("]")) {
                    if (value != null && !value.trim().isEmpty()) {
                        int marks = Integer.parseInt(value);
                        marksValues.add(marks);
                        System.out.println("DEBUG: Added marks value: " + marks);
                    }
                }
            }
            
            System.out.println("DEBUG: Found " + subjectIds.size() + " subjects and " + marksValues.size() + " marks values");
            
            // Validate we have matching arrays
            if (subjectIds.size() != marksValues.size()) {
                System.out.println("ERROR: Mismatch between subject IDs and marks values count");
                return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType + "&error=true";
            }
            
            // Save marks for each subject
            for (int i = 0; i < subjectIds.size(); i++) {
                Marks marks = new Marks();
                marks.setRollNum(rollNum);
                marks.setExamType(examType);
                marks.setSubjectId(subjectIds.get(i));
                marks.setMarks(marksValues.get(i));
                
                System.out.println("DEBUG: Saving marks: " + marks);
                marksService.saveOrUpdateMarks(marks);
            }
            
            return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType + "&success=true";
        } catch (Exception e) {
            System.out.println("ERROR: Exception occurred while saving marks: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/marks/search?rollNum=" + rollNum + "&examType=" + examType + "&error=" + e.getMessage();
        }
    }
} 