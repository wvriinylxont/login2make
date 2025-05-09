package com.example.demo6.execise;

import lombok.*;

import java.util.*;

@Data
public class DepWithEmp {
  private int deptno;
  private String dname;
  private String loc;
  private List<Emp> emps;
}
