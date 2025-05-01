package com.example.demo6.service;

import com.example.demo6.dao.*;
import com.example.demo6.dto.*;
import com.example.demo6.entity.*;
import com.example.demo6.exception.*;
import com.example.demo6.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PostService {
  private static final int BLOCK_SIZE = 5;
  @Autowired
  private PostDao postDao;
  @Autowired
  private CommentDao commentDao;
  
  public PostDto.Pages findAll(int pageno, int pagesize) {
    int totalcount = postDao.count();
    List<Post> posts = postDao.findAll(pageno, pagesize);
    return Demo6Util.getPages(pageno, pagesize, BLOCK_SIZE, totalcount, posts);
  }

  public Map<String, Object> findByPno(int pno, String loginId) {
    // orElseThrow(Supplier)
    // Consumer : 입력은 있고, 출력은 없다
    // Supplier : 입력은 없고, 출력은 있다 -> 예외를 발생
    Map<String, Object> post = postDao.findByPnoWithComments(pno).orElseThrow(()->new EntityNotFoundException("글을 찾을 수 없습니다"));
    if(loginId!=null && !post.get("writer").equals(loginId)) {
      postDao.increaseReadCnt(pno);
    }
    return post;
  }
}
