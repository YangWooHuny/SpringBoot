package com.mega.book.web;


import com.mega.book.domain.posts.Posts;
import com.mega.book.domain.posts.PostsRepository;
import com.mega.book.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import  static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import  static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import  static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private  int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public  void tearDown() throws  Exception{
        postsRepository.deleteAll();
    }

    @Test
    public  void  Posts_등록된다() throws  Exception{
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L); //0Long 이것보다 큰지 비교합니다.
        //isGreaterThan : 큰 경우 true
        //oL은 Long형 값을 비교할떄 0보다 0L이라고 사용한다.(L이라고 뒤에 붙은것은 명시적으로 Long형 값이란 의미)

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}