package com.db.finki.www.build_board.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionHandler {

    private ModelAndView mavBuilder(Exception exception,String message,int status) {
        ModelAndView mav = new ModelAndView("/error_pages/error");
        mav.addObject("exception",exception);
        mav.addObject("message",message);
        mav.addObject("status",status);
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = { AccessDeniedException.class })
    public ModelAndView handleForbidden(AccessDeniedException exception) {
        return mavBuilder(exception,"You requested a resource that you do not have permission to access.",403);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NoResourceFoundException.class })
   public ModelAndView handleNotFound(NoResourceFoundException exception) {
        return mavBuilder(exception,"You requested a resource that does not exist.\nCheck if you entered the url correctly.",404);
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class })
//    public ModelAndView handleNotFound(HttpServerErrorException.InternalServerError exception) {
//        return mavBuilder(exception,"This is a server issue.\nNot your fault :).",500);
//    }


    // @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class })
    // public ModelAndView handleNotFound(Exception exception) {
    //     System.out.println(exception.getMessage());
    //     exception.printStackTrace();
    //     return mavBuilder(exception,"An error occurred.",-1);
    // }
}
