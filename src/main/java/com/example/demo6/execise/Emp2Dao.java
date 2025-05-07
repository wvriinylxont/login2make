package com.example.demo6.execise;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface Emp2Dao {
  @Select("select distinct nvl(position, '사원') from emp2")
  List<String> findPosition();

  @Select("select * from emp2 where emp_type = '정규직'")
  List<Map<String, Object>> findEmp2Byemptype(int emp_type);

  @Select("select * from emp2 where empno = '19960101'")
  List<Map<String, Object>> findEmp2ByEmpno(int empno);
}
