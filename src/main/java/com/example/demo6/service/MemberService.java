package com.example.demo6.service;

import com.example.demo6.dao.*;
import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class MemberService {
  @Autowired
  private MemberDao memberDao;

  public boolean checkUsername(MemberDto.UsernameCheck dto) {
    return !memberDao.existsByUsername(dto.getUsername());
  }
  
  public Member signup(MemberDto.Create dto) {
    // DTO는 화면 따라간다. Entity는 db 따라간다
    // 컨트롤러 : DTO, 데이터베이스는 가급적 Entity로
    // 비밀번호를 암호화했다고 치자
    String encodedPassword = dto.getPassword();
    Member member = dto.toEntity(encodedPassword);
    memberDao.save(member);
    return member;
  }

  public Optional<String> searchUsername(String email) {
    return memberDao.findUsernameByEmail(email);
  }
}
