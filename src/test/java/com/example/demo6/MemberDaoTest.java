package com.example.demo6;

import com.example.demo6.dao.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.security.crypto.password.*;

@SpringBootTest
public class MemberDaoTest {
  @Autowired
  private MemberDao memberDao;
  @Autowired
  private PasswordEncoder encoder;

  //@Test
  public void test() {
    System.out.println(memberDao.loadLoginData("kimminji"));
  }

  @Test
  public void 비밀번호확인() {
    String username = "kimminji";
    String password = memberDao.loadLoginData(username).get().getPassword();
    System.out.println(encoder.matches("123456", password));
  }
}
