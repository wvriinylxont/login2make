package com.example.demo6.security;

import com.fasterxml.jackson.databind.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import java.io.*;

// 403(권한 없음)이 발생 -> html로 403 오류화면 -> ResponseEntity로 바꾸자 -> 여기는 리턴이 void인데 ?
@Component
public class Demo6AccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // 스프링은 json 출력기능이 없다 -> 외부 라이브러리를 사용
    // 스프링의 기본 json 라이브러리는 jackson
    // rest 방식은 데이터 + 상태코드로 응답 -> 스프링이라면 ResponseEntity를 리턴하면 알아서 json으로 바꿔서 출력
    // 스프링 시큐리티의 핸들러들은 사실 자바코드 -> 내가 수동으로 json 변환을 해야한다
    ObjectMapper mapper = new ObjectMapper();
    response.setStatus(403);
    
    // response로 응답하면 한글 출력에 문제가 있다
    response.setContentType("application/json; charset=UTF-8");
    response.getWriter().write(mapper.writeValueAsString("작업 권한이 업습니다"));
  }
}
