package com.example.demo6.execise;

import org.apache.ibatis.annotations.*;

@Mapper
public interface EmpDao2 {
  // 부서정보와 그 부서에서 근무하는 사원정보를 같이 읽어온다
  DepWithEmp findByDeptno(int deptno);
}
