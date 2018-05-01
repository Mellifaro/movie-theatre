package ua.epam.spring.hometask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404()   {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().put("message", "Page is not found");
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(Exception.class)
    public ModelAndView handleConflict(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().put("message", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
