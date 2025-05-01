package com.example.demo6;

import com.example.demo6.dao.*;
import com.example.demo6.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

@SpringBootTest
public class PostDaoTest {
  @Autowired
  private PostDao postDao;

//  @Test
  public void 글때려박기() {
    for(int i=0; i<123; i++) {
      Post p = Post.builder().title(i+"번째글").content("내용없음").writer("spring").build();
      postDao.save(p);
    }
  }

//  @Test
  public void 페이징쿼리테스트() {
    // 집에서 다시 할때는 페이지가 1이므로 123~114
    // 페이지가 1이므로 124~115
    postDao.findAll(1,10).forEach(post->System.out.println(post.getPno()));

    // 집에서 다시 할때는 페이지가 13이면 3~1
    // 페이지가 13이면 4~2
    postDao.findAll(13,10).forEach(post->System.out.println(post.getPno()));
  }

  @Test
  public void findByWithCommentsTest() {
    System.out.println(postDao.findByPnoWithComments(10));
  }
}
