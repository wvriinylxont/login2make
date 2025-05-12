package com.example.demo6.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PostMemberGoodDao {
  // 추천 여부 확인
  @Select("select count(*) from posts_members_good where pno=#{pno} and username=#{loginId} and rownum=1")
  boolean existsByPnoAndUsername(int pno, String loginId);

  // 추천 추가
  @Insert("insert into posts_members_good values(#{pno}, #{loginId})")
  void save(int pno, String loginId);
}
