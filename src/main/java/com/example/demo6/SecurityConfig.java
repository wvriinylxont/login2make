package com.example.demo6;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.*;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
// final로 선언한 필드를 대상으로 하는 생성자를 만들어준다 - 스프링에서 생성자를 이용해 객체를 주입할 때 사용가능
@RequiredArgsConstructor
public class SecurityConfig {
  // 401 오류 처리(로그인이 필요하다)
  private final AuthenticationEntryPoint authenticationEntryPoint;
  // 403 오류처리(권한 오류) -> 로그인 했는데 권한이 없다
  private final AccessDeniedHandler authenticationHandler;
  // 로그인 성공 - 200으로 응답
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  // 로그인 실패 - 409로 응답
  private final AuthenticationFailureHandler authenticationFailureHandler;
  // 로그아웃 성공 - 200으로 응답
  private final LogoutSuccessHandler logoutSuccessHandler;
  
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  // 스프링 시큐리티는 11개의 필터들의 집합체(FilterChain)
  // 필터를 생성, 등록하는 설정 함수
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity config) throws Exception {
    // csrf : MNC 방식에서 타임리프 파일을 위초, 변조하는 것을 막기 위해 사용한다
    //        사용자가 작업한 html파일이 서버가 보내준 파일이 맞는지, 혹시 사용자 html을 조작하지 않았는지 확인하기 위한 랜덤 문자열
    //        화면이 없는 rest에는 전혀 의미없는 개념
    config.csrf(csrf-> csrf.disable());
    config.formLogin(form->form.loginPage("/login").loginProcessingUrl("/login")
        .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler));
    config.logout(logout->logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
    config.exceptionHandling(handler->handler.accessDeniedHandler(authenticationHandler)
        .authenticationEntryPoint(authenticationEntryPoint));
    return config.build();
  }
}
