package com.example.demo6.exception;

import lombok.*;


@AllArgsConstructor
@Getter
public class JobFailException extends RuntimeException{
  private String message;
}
