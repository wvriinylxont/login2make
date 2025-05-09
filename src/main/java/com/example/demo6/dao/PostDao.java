package com.example.demo6.dao;

import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface PostDao {
  // SelectKey의 실행결과를 save의 파라미터인 post에 저장한다 -> 서비스에서 post.getPno()로 시퀀스값에 접근가능
  int save(Post post);

  List<Post> findAll(int pageno, int pagesize);

  int count();

  int increaseReadCnt(int pno);

  Optional<Post> findByPno(int pno);

  Optional<Map<String, Object>> findByPnoWithComments(int pno);

  @Update("update posts set title=#{title}, content=#{content} where pno=#{pno}")
  void update(PostDto.Update dto);

  @Delete("delete from posts where pno=#{pno}")
  void delete(Integer pno);
}
