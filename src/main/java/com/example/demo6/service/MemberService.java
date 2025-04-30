package com.example.demo6.service;

import com.example.demo6.dao.*;
import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import com.example.demo6.util.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

@Service
public class MemberService {
  @Autowired
  private MemberDao memberDao;

  public boolean checkUsername(MemberDto.UsernameCheck dto) {
    return !memberDao.existsByUsername(dto.getUsername());
  }
  
  public Member signup(MemberDto.Create dto) {
    // 1. 비밀번호 암호화
    // DTO는 화면 따라간다. Entity는 db 따라간다
    // 컨트롤러 : DTO, 데이터베이스는 가급적 Entity로
    // 비밀번호를 암호화했다고 치자
    String encodedPassword = dto.getPassword();
    // 2. 프사를 업로드했다면 저장을 위해 base64 인코딩
    MultipartFile profile = dto.getProfile();
    String base64Image = "";
//    이렇게 input이 있지만 선택은 안했다 -> 백에서 값을 꺼내면 null이 아니라 비어있다
    // <input type='file' name='profile'> -> 입력은 안했다 -> 서버에서 꺼내면 null이 아니라 ""이다
    if(!profile.isEmpty()) {
      try {
        base64Image = Demo6Util.convertToBase64(profile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // 3. 암호화된 비밀번호, base64이미지를 가지고 dto를 member로 변환
    Member member = dto.toEntity(encodedPassword, base64Image);
    memberDao.save(member);
    return member;
  }

  public Optional<String> searchUsername(String email) {
    return memberDao.findUsernameByEmail(email);
  }
  
  public Optional<String> getTemporaryPassword(MemberDto.GeneratePassword dto) {
    // 1. 아이디와 이메일이 일치하는 사용자가 있는 지 확인
    // 2. 사용자가 없을 경우 비어있는 Optional을 리턴 -> 컨트롤러에서 if문으로 처리
    // 3. 있을 경우 임시비밀번호를 생성
    // 4. 임시비밀번호를 암호화해서 사용자 정보를 업데이트
    // 5. 비밀번호를 리턴
    boolean isExist = memberDao.existsByUsernameAndEmail(dto);
    if(!isExist)
      return Optional.empty();
    String newPassword = RandomStringUtils.secure().nextAlphabetic(20);
    memberDao.updatePassword(dto.getUsername(), newPassword);
    return Optional.ofNullable(newPassword);
  }
}
