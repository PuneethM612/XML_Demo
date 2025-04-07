package com.bnpp.pb.lynx.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bnpp.pb.lynx.service.MarksService;

public class RankerController {
    
    private MarksService marksService;
    
    public void setMarksService(MarksService marksService) {
        this.marksService = marksService;
    }
    
    @GetMapping("/rankers")
    public String rankerForm(Model model) {
        model.addAttribute("examTypes", new String[]{"monthly", "mid", "annual"});
        return "ranker_search";
    }
    
    @GetMapping("/rankers/top")
    public String getTopRankers(@RequestParam("examType") String examType, Model model) {
        model.addAttribute("topRankers", marksService.getTopRankers(examType, 3));
        model.addAttribute("examType", examType);
        return "ranker_view";
    }
} 