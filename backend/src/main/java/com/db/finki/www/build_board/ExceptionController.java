package com.db.finki.www.build_board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {
    private final HttpHeaders corsHeaders = new HttpHeaders(   MultiValueMap.fromSingleValue(
            Map.of(
                    "Access-Control-Allow-Origin", "http://localhost:5173",
                    "Access-Control-Allow-Credentials", "true",
                    "Access-Control-Allow-Headers", "Content-Type, Authorization",
                    "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"
                  )
                                                                                               ));

    @ExceptionHandler(JOSEException.class)
    public ProblemDetail handleJOSEException(JOSEException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Sorry the server cannot verify that JOSE header");
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ProblemDetail handleJsonProcessingException(JsonProcessingException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,"The server failed to generate JSON");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Username not found");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        if(e.getMessage().contains("duplicate key"))
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"The username already exists");
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Data integrity violation");
    }

    @ExceptionHandler(ServletException.class)
    public ProblemDetail handleServletException(ServletException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage())
                , corsHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
