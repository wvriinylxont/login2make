package com.example.demo6.dao;

import com.example.demo6.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface MemberDao {
  @Select("select count(*) from members where username=#{username}")
  boolean existsByUsername(String username);

  int save(Member member);

  @Select("select username from members where email=#{email} and rownum=1")
  // optional null체크를 알려주는거
  Optional<String> findUsernameByEmail(String email);
}
