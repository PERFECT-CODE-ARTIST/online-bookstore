package com.bookstore.online.domain.user.controller;

import com.bookstore.online.global.exception.SigninException;
import com.bookstore.online.global.exception.SignupValidException;
import javax.naming.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(SignupValidException.class)
  public ResponseEntity<?> validException(SignupValidException e) {
    return ResponseEntity.badRequest().body(e.getFieldErrors());
  }

  @ExceptionHandler(SigninException.class)
  public ResponseEntity<?> signinException(SigninException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

}
