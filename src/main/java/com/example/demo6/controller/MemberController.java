package com.example.demo6.controller;

import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import com.example.demo6.service.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

// MVC 방식은 html로 응답 vs REST 방식은 데이터 + 상태코드로 응답
// @Controller MVC와 REST 방식을 모두 지원
// @RestControllers는 REST방식만 지원(ModelAndView 리턴 불가)
@Validated
@RestController
public class MemberController {
  @Autowired
  private MemberService service;

  @PreAuthorize("isAnonymous()")
  @Operation(summary = "아이디 확인", description = "아이디가 사용가능한 지 확인")
  @GetMapping("/api/members/check-username")
  public ResponseEntity<String> checkUsername(@ModelAttribute @Valid MemberDto.UsernameCheck dto, BindingResult br) {
    boolean result = service.checkUsername(dto);
    if(result)
      return ResponseEntity.ok("사용가능합니다");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 아이디입니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary = "회원가입", description = "회원가입 및 프로필 사진 업로드")
  @PostMapping(value="/api/members/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Member> signup(@ModelAttribute @Valid MemberDto.Create dto, BindingResult br) {
    Member member = service.signup(dto);
    return ResponseEntity.status(200).body(member);
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary = "아이디 찾기", description = "가입한 이메일로 아이디를 찾는다")
  @GetMapping("/api/members/username")
  public ResponseEntity<String> searchUsername(@RequestParam @Email String email) {
    // @RequestParam으로 입력받을 때 swagger가 검증 오류를 출력하지 않고 ControllerAdvice로 넘기더라
    Optional<String> result = service.searchUsername(email);
    if(result.isPresent())
      return ResponseEntity.ok(result.get());
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary = "임시비밀번호 발급", description = "아이디와 이메일로 임시비밀번호를 발급")
  @PutMapping("/api/members/password")
  public ResponseEntity<String> getTemporaryPassword(@ModelAttribute @Valid MemberDto.GeneratePassword dto, BindingResult br) {
    Optional<String> 임시비밀번호 = service.getTemporaryPassword(dto);
    if(임시비밀번호.isPresent())
      return ResponseEntity.ok(임시비밀번호.get());
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
  }

  // 내정보보기
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "내정보 보기", description = "내정보 보기")
  @GetMapping("/api/members/member")
  public ResponseEntity<MemberDto.Read> read(Principal principal) {
    MemberDto.Read dto = service.read(principal.getName());
    return ResponseEntity.ok(dto);
  }

  // 비밀번호 변경
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "비밀번호 변경", description = "기존 비밀번호, 새 비밀번호로 비밀번호 변경")
  @PatchMapping("/api/members/password")
  public ResponseEntity<String> changePassword(@ModelAttribute @Valid MemberDto.PasswordChange dto, BindingResult br, Principal principal) {
    boolean result = service.changePassword(dto, principal.getName());
    if(result)
      return ResponseEntity.ok("비밀번호 변경");
    return ResponseEntity.status(409).body("비밀번호 변경 실패");
  }

  // 회원 탈퇴
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "회원 탈퇴", description = "로그아웃시킨 후 회원 탈퇴")
  @DeleteMapping("/api/members/member")
  public ResponseEntity<String> resgin(Principal principal, HttpSession session) {
    service.resign(principal.getName());
    session.invalidate();
    return ResponseEntity.ok("회원 탈퇴");
  }
}
