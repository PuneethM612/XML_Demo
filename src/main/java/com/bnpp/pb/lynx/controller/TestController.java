package com.bnpp.pb.lynx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @GetMapping("/test/template")
    public String testTemplate(Model model) {
        logger.info("Testing Thymeleaf template resolution");
        model.addAttribute("message", "This is a test message");
        // Test with a template known to work
        return "error";
    }
    
    @GetMapping("/test/marks-template")
    public String testMarksTemplate(Model model) {
        logger.info("Testing marks_add_multiple template resolution");
        model.addAttribute("message", "This is a test message");
        // Return the exact same string as in MarksController
        return "marks_add_multiple";
    }
    
    @GetMapping("/test/simple")
    public String testSimpleTemplate(Model model) {
        logger.info("Testing simple template resolution");
        model.addAttribute("message", "This is a test message for the simple template");
        return "simple_test";
    }
} 