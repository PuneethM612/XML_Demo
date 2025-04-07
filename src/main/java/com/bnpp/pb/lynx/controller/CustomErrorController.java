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
        // Get error details
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exceptionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        
        // Add attributes to the model
        model.addAttribute("status", status != null ? status.toString() : "Unknown");
        model.addAttribute("message", message != null ? message.toString() : "An error occurred");
        model.addAttribute("exceptionType", exceptionType != null ? exceptionType.toString() : "Unknown exception");
        
        // Debug print
        System.out.println("DEBUG: Error handling - status=" + status + ", message=" + message);
        
        // Check if it's a Thymeleaf template error
        String exceptionTypeStr = exceptionType != null ? exceptionType.toString() : "";
        if (exceptionTypeStr.contains("Thymeleaf") || 
            exceptionTypeStr.contains("Template") || 
            (message != null && message.toString().contains("template"))) {
            
            // Get exception for more details
            Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            if (exception != null) {
                String fullMessage = exception.toString();
                model.addAttribute("templateName", extractTemplateName(fullMessage));
                
                System.out.println("DEBUG: Template error detected! Full message: " + fullMessage);
                System.out.println("DEBUG: Returning template-error view");
                
                return "template-error";
            }
        }
        
        System.out.println("DEBUG: Returning regular error view");
        return "error";
    }
    
    private String extractTemplateName(String errorMessage) {
        // Extract template name from error message
        // Example format: "... template: \"class path resource [templates/marks _add _multiple.html]\" ..."
        if (errorMessage != null && errorMessage.contains("template:") && errorMessage.contains("[") && errorMessage.contains("]")) {
            int start = errorMessage.indexOf("[") + 1;
            int end = errorMessage.indexOf("]", start);
            if (start > 0 && end > start) {
                return errorMessage.substring(start, end);
            }
        }
        return "Unknown template";
    }
} 