package com.example.demo6.advice;

import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CustomControllerAdvice {
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> constraintViolationException() {
    return ResponseEntity.status(409).body("잘못된 입력 형식");
  }
}
