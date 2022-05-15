package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.ReviewRequestDto;
import com.blackshirts.movieshelf.service.ReviewService;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ReviewService reviewService = Mockito.mock(ReviewService.class);

    @Autowired
    private UserService userService = Mockito.mock(UserService.class);


//    @BeforeEach
//    void insert() {
//        for (int i = 1; i <= 3; i++) {
//            ReviewCreateRequestDto requestDto =
//                    new ReviewCreateRequestDto();
//            requestDto.setUser(userService.getUserByUserEmail("kionn@gmail.com"));
//            requestDto.setTitle("title" + i);
//            requestDto.setContent("content" + i);
//            reviewService.create(requestDto);
//        }
//    }

    @Test
    void create() throws Exception {

        String jwtTokenProviderToken = jwtTokenProvider.createToken("kionn@gmail.com");
        assertNotNull(jwtTokenProviderToken);

        ReviewRequestDto requestDto = new ReviewRequestDto("kionn@gmail.com", "title5", "content5");

        mockMvc.perform(post("/api/v3/review")
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void searchAllDesc() throws Exception{

        String jwtTokenProviderToken = jwtTokenProvider.createToken("kionn@gmail.com");
        assertNotNull(jwtTokenProviderToken);

        mockMvc.perform(get("/api/v3/review")
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void searchById() {
//        String jwtTokenProviderToken = jwtTokenProvider.createToken("kionn@gmail.com");
//        assertNotNull(jwtTokenProviderToken);
//
//        mockMvc.perform(get("/api/v3/review")
//                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    void searchByUser() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}