package com.example.demo6.security;

import com.example.demo6.dao.*;
import com.example.demo6.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import java.io.*;

// 로그인에 실패하면 "로그인에 몇회 실패했습니다"라고 출력
// 로그인에 5번 실패하면 계정을 블록한 다음 "계정이 블록되었습니다"라고 출력
@Component
public class Demo6AuthenticationFailureHandler implements AuthenticationFailureHandler {
  @Autowired
  private MemberDao memberDao;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    // 과일의 자식으로 사과, 바나나, 딸기가 있다면
    // 과일 참조변수 ob가 지금 가리키는 대상은 사과, 바나나, 딸기가 모두 가능한데.. 그래서 어떤거야?
    // ob instanceof 사과 -> true 또는 false
    
    // ※ 로그인에 실패한 경우
    // 1. 로그인 시도한 아이디를 가져와서
    // 2. db에서 사용자 정보를 읽어온다
    // 3. 로그인 실패 횟수가 3회이하라면 로그인 실패 횟수 증가 -> "로그인에 몇회 실패했습니다"
    // 4. 로그인 실패횟수가 4회라면 5회로 증가시키고 계정을 블록 -> "로그인에 5회 실패해 계정이 블록되었습니다"
    
    // ※ 계정이 이미 블록된 경우
    // 5. "블록된 계정입니다"

    String message = "알 수 없는 이유로 로그인에 실패했습니다";
    if(exception instanceof BadCredentialsException) {
      // 아이디나 비밀번호가 틀린 경우
      String 로그인_시도_아이디 = request.getParameter("username");
      if(memberDao.로그인실패횟수(로그인_시도_아이디).isEmpty()) {
        // a-1. 아이디가 틀린 경우
        message = "아이디를 확인하세요";
      } else {
        // a-2. 비밀번호가 틀린경우
        memberDao.로그인실패횟수증가(로그인_시도_아이디);
        int count = memberDao.로그인실패횟수(로그인_시도_아이디).get();
        if(count>=5) {
          // a-2-1. 비밀번호가 5회이상 틀렸다면 계정 블록
          memberDao.계정블록(로그인_시도_아이디);
          message = "로그인에 5회 실패해 계정이 블록되었습니다";
        } else {
          // a-2-a. 비밀번호가 4회이하로 틀린경우
          message = "로그인에" + count + "회" + "실패했습니다";
        }
      }
    } else if(exception instanceof LockedException) {
      // 계정이 블록된 경우
      message = "로그인에 5회이상 실패로 블록된 계정입니다.";
    }
    ResponseUtil.sendJsonResponse(response, 401, message);
  }
}
