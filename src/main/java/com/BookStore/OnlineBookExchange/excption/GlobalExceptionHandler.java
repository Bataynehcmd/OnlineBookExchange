package com.BookStore.OnlineBookExchange.excption;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(
            IllegalArgumentException e,
            Model model) {

        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(
            ResourceNotFoundException e,
            Model model) {

        model.addAttribute("error", e.getMessage());
        return "error";
    }
}