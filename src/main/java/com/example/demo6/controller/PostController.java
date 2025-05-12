package com.example.demo6.controller;

import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import com.example.demo6.service.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

// @Validated는 스프링 검증, 없으면 자바 표준 검증 수행. 스프링 검증이 좀 더 간략화
@Validated
@RestController
public class PostController {
  @Autowired
  private PostService service;

  @Operation(summary = "페이징", description = "기본 페이지번호 1, 페이지크기 10으로 페이징")
  @GetMapping("/posts")
  public ResponseEntity<PostDto.Pages> findAll(@RequestParam(defaultValue = "1") int pageno,
                                               @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.ok(service.findAll(pageno, pagesize));
  }
  
  // @RequestParam이나 @ModelAttribute에서는 사용자가 값을 넘기지 않으면 null이 되고 널체크로 오류를 발견한다
  // @PathVariable은 사용자가 넘기는 값이 주소의 일부 !!
  // - 만약 바로 아래 주소에서 pno룰 안넘겼다면 get / posts. 즉 주소가 아예 다르다
  @Operation(summary = "글읽기", description = "글읽기")
  @GetMapping("/posts/post")
  public ResponseEntity<Map<String, Object>> findByPno(@RequestParam int pno, Principal principal) {
    // 로그인했으면 로그인 아이디, 비로그인이면 null을 대입
    String loginId = principal==null? null : principal.getName();
    return ResponseEntity.ok(service.findByPno(pno, loginId));
    
    // MemberController : 컨트롤러에서 if문 걸어서 200응답, 또는 409응답을 만들어낸다
    // 또 다른 방법으로는 내가 원하지 않는 방향으로 진행되면 서비스에서 예외 발생 -> ControllerAdvice로 넘긴다
    // 컨트롤러는 200응답(바람직한 흐름)만 담당, 바람직하진 않은 흐름은 어드바이스에서 담당
  }
  
  @Operation(summary = "글쓰기")
  @Secured("ROLE_USER")
  @PostMapping("/posts/new")
  public ResponseEntity<Post> write(@ModelAttribute @Valid PostDto.PostCreateDto dto, BindingResult br, Principal principal) {
    Post post = service.write(dto, principal.getName());
    return ResponseEntity.ok(post);
  }
  
  @Operation(summary = "글변경", description = "글번호로 제목과 내용 변경")
  @Secured("ROLE_USER")
  @PostMapping("/posts/post")
  public ResponseEntity<String> update(@ModelAttribute @Valid PostDto.Update dto, BindingResult br, Principal principal) {
    service.update(dto, principal.getName());
    return ResponseEntity.ok("글을 변경 했습니다");
  }
  
  @Operation(summary = "삭제")
  @Secured("ROLE_USER")
  @DeleteMapping("/posts/post")
  public ResponseEntity<String> delete(@RequestParam @NotNull Integer pno, Principal principal) {
    service.delete(pno, principal.getName());
    return ResponseEntity.ok("글을 삭제 했습니다");
  }
  
  @Secured("ROLE_USER")
  @PutMapping("/posts/good")
  @Operation(summary = "글추천", description = "이미 추천한 글은 재추천 할 수 없습니다")
  public ResponseEntity<Integer> 추천(@RequestParam @NotNull Integer pno, Principal principal) {
    int newGoodCnt = service.추천(pno, principal.getName());
    return ResponseEntity.ok(newGoodCnt);
  }
}
