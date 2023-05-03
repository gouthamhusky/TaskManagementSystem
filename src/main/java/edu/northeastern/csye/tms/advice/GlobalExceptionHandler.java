package edu.northeastern.csye.tms.advice;

import edu.northeastern.csye.tms.exception.DatabaseDownException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseDownException.class)
    public String handleCustomException(DatabaseDownException ex, Model model) {
        model.addAttribute("errorMessage", "CustomException: " + ex.getMessage());

        return "error-page";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleCustomException(EntityNotFoundException ex, Model model) {
        String errorMessage = "Username provided was not found";
        model.addAttribute("errorMessage", errorMessage);

        return "error-page";
    }


    @ExceptionHandler(value = {PersistenceException.class, IllegalArgumentException.class})
    public String handlePersistenceException(PersistenceException ex){

        return "error-page";
    }

    @ExceptionHandler(value = {TemplateInputException.class, TemplateProcessingException.class})
    public String handleThymeleafException(){

        return "error-page";
    }

}
