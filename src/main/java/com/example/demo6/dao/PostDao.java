package com.example.demo6.dao;

import com.example.demo6.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface PostDao {
  // SelectKey의 실행결과를 save의 파라미터인 post에 저장한다 -> 서비스에서 post.getPno()로 시퀀스값에 접근가능
  @SelectKey(statement = "select posts_seq.nextval from dual", before = true, keyProperty = "pno", resultType = Integer.class)
  @Insert("insert into posts values(#{pno}, #{title}, #{content}, #{writeTime}, #{writer}, #{readCnt}, #{goodCnt}, #{badCnt})")
  int save(Post post);

  // select * from posts order by pno desc offset 시작위치 rows fetch next 개수 rows only
  // pageno ==1 offest 0 rows fetch next 10 rows only
  // pageno== 2 offeest 10 rows fetch next 10 rows only
  @Select("select pno, title, writer, write_time, read_cnt from posts order by pno desc offset (#{pageno}-1)*#{pagesize} rows fetch next #{pagesize} rows only")
  List<Post> findAll(int pageno, int pagesize);

  @Select("select count(*) from posts")
  int count();

  @Update("update posts set read_cnt=read_cnt+1 where pno=#{pno}")
  int increaseReadCnt(int pno);

  @Select("select * from posts where pno=#{pno}")
  Optional<Post> findByPno(int pno);
}
