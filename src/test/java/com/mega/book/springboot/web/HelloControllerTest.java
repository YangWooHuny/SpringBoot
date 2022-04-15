package com.mega.book.springboot.web;

import com.mega.book.web.HelloController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;

@RunWith(SpringRunner.class)
/* 테스트를 진행할 떄 Junit에 내장된 실행자와 다른 실행자를 실행 시킵니다.
*  다른 실행자 > SpringRunner.class
*  스프링 부트 테스트와 Junit 사이에 연결자 역할*/
@WebMvcTest(controllers = HelloController.class)
/* Web에 집중 할수 있는 어노테이션
*  위에가 선언되면 @Controller @ControllerAdvice등 사용가능
*  @Service, @Component, @Repository 사용불가
*  컨트롤만 사용가능*/
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public  void  helloReturn() throws Exception{
        String hello = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(hello));
    }

    @Test
    public  void helloDtoReturn() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto").param("name",name)
                .param("amount",String.valueOf(amount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(amount)));
    }

}
