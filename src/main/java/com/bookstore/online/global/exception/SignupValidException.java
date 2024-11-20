package com.bookstore.online.global.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

public class SignupValidException extends RuntimeException {

  @Getter
  private List<FieldError> fieldErrors;

  public SignupValidException(List<FieldError> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }
}