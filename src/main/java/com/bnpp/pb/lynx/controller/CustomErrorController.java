package com.bnpp.pb.lynx.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CustomErrorController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get error details from request attributes
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object trace = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        logger.error("Error occurred: status={}, path={}, message={}", status, path, message);
        
        if (exception != null) {
            logger.error("Exception details: ", (Throwable) exception);
        }
        
        model.addAttribute("status", status != null ? status : "Unknown");
        model.addAttribute("error", "An error occurred");
        model.addAttribute("message", message != null ? message : "No error message available");
        model.addAttribute("path", path);
        model.addAttribute("timestamp", new java.util.Date());
        
        if (exception != null) {
            model.addAttribute("exception", exception);
            model.addAttribute("trace", generateStackTrace((Throwable) exception));
        }
        
        return "error";
    }

    private String generateStackTrace(Throwable exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(exception.getClass().getName()).append(": ").append(exception.getMessage()).append("\n");
        
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append("\tat ").append(element).append("\n");
        }
        
        return sb.toString();
    }
} 