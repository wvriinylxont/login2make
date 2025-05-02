package com.example.demo6.security;

import com.example.demo6.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import java.io.*;

// 403(권한 없음)을 처리하는 자바코드
// 403(권한 없음)이 발생 -> html로 403 오류화면 -> ResponseEntity로 바꾸자 -> 여기는 리턴이 void인데 ?
@Component
public class Demo6AccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ResponseUtil.sendJsonResponse(response, 403, "작업 권한이 없습니다");
  }
}
