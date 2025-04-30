package com.example.demo6.service;

import com.example.demo6.dao.*;
import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import com.example.demo6.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PostService {
  private static final int BLOCK_SIZE = 5;
  @Autowired
  private PostDao postDao;
  
  public PostDto.Pages findAll(int pageno, int pagesize) {
    int totalcount = postDao.count();
    List<Post> posts = postDao.findAll(pageno, pagesize);
    return Demo6Util.getPages(pageno, pagesize, BLOCK_SIZE, totalcount, posts);
  }

  public Post findByPno(int pno, String loginId) {
    // loginId : 비로그인이면 null. 로그인 했으면 로그인 아이디
    // findByPno한 결과 Optional에서 값을 꺼내서 post에 저장해라
    // 만약 없다면 예외를 발생시켜라 -> ControllerAdvice로 가서 오류메시지를 출력
    Post post = postDao.findByPno(pno).orElseThrow(()->new RuntimeException());
    if(!post.getWriter().equals(loginId))
      postDao.increaseReadCnt(pno);
    return post;
  }
}
