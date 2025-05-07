package com.example.demo6.execise;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface Emp2Dao {
  // emp2에서 직위(position)를 출력
  @Select("select distinct nvl(position, '사원') from emp2")
  List<String> findPosition();

  //emp2에서 emp_type으로 검색해 사원을 출력
  @Select("select * from emp2 where emp_type = '정규직'")
  List<Map<String, Object>> findEmp2Byemptype(int emp_type);

  //emp2에서 사번을 입력받아 사원을 출력
  @Select("select * from emp2 where empno = '19960101'")
  List<Map<String, Object>> findEmp2ByEmpno(int empno);
}
