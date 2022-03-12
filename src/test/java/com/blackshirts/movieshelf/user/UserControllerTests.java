package com.blackshirts.movieshelf.user;

import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.dto.UserSignupRequestDto;
import com.blackshirts.movieshelf.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    @Test
//    public void login() throws Exception {
//        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("first@example.com", "1111");
//
//        mockMvc.perform(post("/v1/signup")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
    @Test
    public void getUserAll() throws Exception{
        mockMvc.perform(get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(403));
        //access denied => 403
    }
}
