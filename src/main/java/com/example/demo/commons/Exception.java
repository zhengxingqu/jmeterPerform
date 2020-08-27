package com.example.demo.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@ControllerAdvice
public class Exception {
    private final Logger logger = LoggerFactory.getLogger(Exception.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return R.error().message(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handHttpRequestException(HttpRequestMethodNotSupportedException e) {
        return R.error().message(e.getMessage());
    }




}
