package com.bookstore.online.global.controller;

import com.bookstore.online.global.exception.DatabaseException;
import com.bookstore.online.global.exception.SigninException;
import com.bookstore.online.global.exception.SignupValidException;
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

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<?> databaseException(DatabaseException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
