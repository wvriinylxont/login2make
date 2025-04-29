package com.example.demo6.dto;

import com.example.demo6.entity.*;
import jakarta.validation.constraints.*;
import lombok.*;


public class MemberDto {
  @Data
  public static class UsernameCheck {
    @NotEmpty
    @Pattern(regexp = "^[a-z0-9]{6,10}$")
    private String username;
  }

  @Data
  public static class Create {
    // 아이디는 소문자와 문자 6~10자 -> 문자열 패턴을 검증할 때 사용하는 기술 : "정규식"
    // [a-z] : 하나의 글자 ( 소문자 한개 ), [0-9] : 숫자 1개
    // [a-z0-9] : 범위들을 나열하면 또는으로 연결 -> 소문자 또는 숫자 1글자
    // [a-z0-9]{6,10} : 소문자 또는 숫자 6~10자(을 포함하는)
    //                  현재 패턴에 "11111111111"을 주면 통과
    // {}는 글자수
    // 아이디는 소문자와 숫자가 6글자이상, 10글자를 초과하면 안된다
    // ^[a-z0-9]{6,10}$ : ^ 시작한다는 뜻, $ 끝난다
    @NotEmpty // 검증
    @Pattern(regexp = "^[a-z0-9]{6,10}$")
    private String username;
    @NotEmpty
    // 비밀번호는 대문자,소문자,숫자 6~10글자
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$")
    private String password;
    @Email
    @NotEmpty
    private String email;
    // MultipartFile : 파일을 업로드 했을때의 타입
    private String profile;

    // DTO를 Entity로 변환
    public Member toEntity(String encodedPassword) {
      return  Member.builder().username(username).password(encodedPassword).email(email).build();
    }
  }
}
