package com.example.demo6.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder // 빌더가 있으면 All, NO 추가
public class Member {
  private String username;
  @JsonIgnore // 출력이 절대 안나감
  private String password;
  private String email;
  // 프로필 사진 이름
  private String profile;

  @Builder.Default
  private LocalDate joinDay = LocalDate.now();
  @Builder.Default
  private Role role = Role.USER;
  @Builder.Default
  private Level level = Level.NORMAL;
  // 역할 : USER, MANAGER, ADMIN

  // 로그인 관련된 데이터 : 로그인 실패 횟수, 계정 블록 여부
  @Builder.Default
  private int failedAttempts = 0;
  @Builder.Default
  private boolean isLock = false;
}
