package com.example.demo6;

import com.example.demo6.execise.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class Emp2DaoTest {
  @Autowired
  private Emp2Dao emp2Dao;

  //@Test
  public void findPositionTest() {
    assertEquals(6, emp2Dao.findPosition().size());
  }

  @Test
  public void findEmp2ByemptypeTest() {
    assertEquals(10, emp2Dao.findEmp2Byemptype(1).size());
  }

  @Test
  public void findEmp2ByEmpnoTest() {
    assertEquals(1, emp2Dao.findEmp2ByEmpno(19960101).size());
  }

}
