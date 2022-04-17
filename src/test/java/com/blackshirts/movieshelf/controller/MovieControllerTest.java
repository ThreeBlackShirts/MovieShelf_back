package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
//@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired //스프링이 관리하는 빈 주입을 받는다.
    private MockMvc mockMvc; // 웹 API 테스트 시 사용(HTTP GET, POST), 스프링 MVC의 시작

    @MockBean //특정 Bean 을 모의 객체로 주입받아 사용
    private MovieService movieService;

    @Test
    public void movieSave() throws Exception {
        mockMvc.perform(get("/api/movie/moviedatainsert"))
                .andExpect(status().isOk()); //status검증
    }

    @Test
    public void findAllMovies() throws Exception {
        mockMvc.perform(get("/api/movie/movielist"))
                .andExpect(status().isOk()) //status검증
                .andDo(print());
    }
}