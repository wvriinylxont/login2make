package com.example.demo6;

import com.example.demo6.execise.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

@SpringBootTest
public class EmpDao2Test {
  @Autowired
  private EmpDao2 empDao;

  @Test
  public void findByDeptno() {
    System.out.println(empDao.findByDeptno(10));
  }
}
