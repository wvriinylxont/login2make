package com.example.demo6.entity;

import lombok.*;

@Getter
@AllArgsConstructor   //( 생성자 추가)
public enum Level {
  NORMAL("고마운 분"), SILVER("귀한 분"), GOLD("천생연분");

  // enum에 한글화 파라미터를 추가하면, 추가한 파라미터를 필드로
  private final String name;
}
