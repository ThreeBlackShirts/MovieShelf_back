package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.UserSignupRequestDto;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        userService = new UserService(userRepository,jwtTokenProvider,passwordEncoder);
    }

    @Test
    void saveUser() {
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto("email@example.com", "name", "password", "nickname");
        //2.
        Long id = userService.signUp(userSignupRequestDto);
        //3.
        assertThat(id).isEqualTo(userRepository.findById(id));
    }
}