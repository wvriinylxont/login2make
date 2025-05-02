package com.example.demo6.advice;

import org.springframework.http.*;
import org.springframework.web.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.*;
import org.springframework.web.servlet.resource.*;

@RestControllerAdvice
public class ReuqestFaultAdvice {
  // 401 : 인증이 필요하다 -> 스프링 시큐리티

  // 403 인가(권한확인)
  
  // 404 처리
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<String> handleState404(NoResourceFoundException e) {
    // No static resource posts/postaaa -> ["No", "static", "resource", "posts/postaaa"]로 분리
    String[] messages = e.getMessage().split(" ");
    // 분리된 문자열 배열의 마지막 원소를 꺼낸다 : "posts/postaaa"
    String url = messages[messages.length-1];
    // 점을 제거하자
    url = url.substring(0, url.length()-1);
    return ResponseEntity.status(404).body("잘못된 주소입니다 : " + url);
  }

  // "/post?pno = 100과 같이 필수 파라미터가 있지만" 생략된 경우(/post)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
    return ResponseEntity.status(400).body(e.getMessage());
  }

  // 파라미터 타입이 잘못된 400(int가 필요한데 /post?pno=aaa)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    return ResponseEntity.status(400).body(e.getMessage());
  }

  // 405 : 잘못된 메소드
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<String> handState405() {
    return ResponseEntity.status(4405).body("잘못된 메소드");
  }
}
