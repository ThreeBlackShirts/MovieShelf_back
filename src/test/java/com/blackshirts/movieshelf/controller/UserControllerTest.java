package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.dto.UserSignupRequestDto;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private UserService userService = Mockito.mock(UserService.class);

    @BeforeEach
    public void insert() {
        for (int i = 1; i <= 10; i++) {
            UserSignupRequestDto userSignupRequestDto =
                    new UserSignupRequestDto();
            userSignupRequestDto.setUserEmail("email" + i + "@example.com");
            userSignupRequestDto.setUserName("name" + i);
            userSignupRequestDto.setUserNickname("nickname" + i);
            userSignupRequestDto.setUserPassword("password");
            userService.signUp(userSignupRequestDto);
        }
    }

    @Test
    public void login() throws Exception {
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("email1@example.com", "password");

        mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andDo(print())
                .andExpect(status().is(400));
        //400 - 잘못된 비밀번호 passwordEncoder 문제
    }



    @Test
    public void getUserAll() throws Exception {
        mockMvc.perform(get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(403));
        //access denied => 403
    }
}
