package com.bnpp.pb.lynx.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exceptionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        
        model.addAttribute("status", status != null ? status.toString() : "Unknown");
        model.addAttribute("message", message != null ? message.toString() : "An error occurred");
        model.addAttribute("exceptionType", exceptionType != null ? exceptionType.toString() : "Unknown exception");
        
        return "error";
    }
} 