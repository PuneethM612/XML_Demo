package com.bnpp.pb.lynx.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bnpp.pb.lynx.model.Subject;
import com.bnpp.pb.lynx.service.SubjectService;

public class SubjectController {
    
    private SubjectService subjectService;
    
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    @GetMapping("/subjects")
    public String getAllSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("subject", new Subject());
        return "subjects";
    }
    
    @PostMapping("/subjects")
    public String addSubject(@ModelAttribute Subject subject, Model model) {
        if (subject != null && subject.getSubjectName() != null && !subject.getSubjectName().trim().isEmpty()) {
            subjectService.insertSubjectIfNotExists(subject);
            return "redirect:/subjects";
        }
        
        model.addAttribute("error", "Subject name cannot be empty!");
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects";
    }
} 