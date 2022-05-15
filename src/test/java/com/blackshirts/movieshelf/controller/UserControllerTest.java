package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.dto.UserUpdateRequestDto;
import com.blackshirts.movieshelf.dto.UserResponseDto;
import com.blackshirts.movieshelf.dto.UserSignupRequestDto;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService = Mockito.mock(UserService.class);

    @BeforeEach
    void insert() {
        for (int i = 1; i <= 3; i++) {
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

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void signup() throws Exception {
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto("email4@example.com", "john", "password", "nick");

        mockMvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userSignupRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserOneById() throws Exception {
        String jwtTokenProviderToken = jwtTokenProvider.createToken("email2@example.com");
        assertNotNull(jwtTokenProviderToken);

        UserResponseDto userResponseDto = userService.findUserByEmail("email2@example.com");

        String url = "/api/v1/user/id/" + userResponseDto.getUserId();

        mockMvc.perform(get(url)
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserOneByEmail() throws Exception {
        String jwtTokenProviderToken = jwtTokenProvider.createToken("email2@example.com");
        assertNotNull(jwtTokenProviderToken);

        UserResponseDto userResponseDto = userService.findUserByEmail("email2@example.com");
        String url = "/api/v1/user/email/" + userResponseDto.getUserEmail();
        mockMvc.perform(get(url)
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserAll() throws Exception {
        String jwtTokenProviderToken = jwtTokenProvider.createToken("email1@example.com");
        assertNotNull(jwtTokenProviderToken);

        mockMvc.perform(get("/api/v1/users")
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception {
        String jwtTokenProviderToken = jwtTokenProvider.createToken("email3@example.com");
        assertNotNull(jwtTokenProviderToken);

        UserResponseDto userResponseDto = userService.findUserByEmail("email3@example.com");
        UserUpdateRequestDto userRequestDto = new UserUpdateRequestDto(userResponseDto.getUserId(), "new_nick_name", "");

        mockMvc.perform(put("/api/v1/user")
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        String jwtTokenProviderToken = jwtTokenProvider.createToken("email3@example.com");
        assertNotNull(jwtTokenProviderToken);

        UserResponseDto userResponseDto = userService.findUserByEmail("email3@example.com");

        String url = "/api/v1/user/" + userResponseDto.getUserId();
        mockMvc.perform(delete(url)
                        .header("X-AUTH-TOKEN", jwtTokenProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @AfterEach
    public void deleteAll() {
        for (int i = 1; i <= 4; i++) {
            UserSignupRequestDto userSignupRequestDto =
                    new UserSignupRequestDto();
            userSignupRequestDto.setUserEmail("email" + i + "@example.com");

            try {
                userService.delete(userService.findUserByEmail(userSignupRequestDto.getUserEmail()).getUserId());
            } catch (UsernameNotFoundException e) {

            }
        }
    }
}
