package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.UserSignupRequestDto;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void existsByUserEmail() {
//        //given
//        User user = new User("servicetest@email.com","TestMan","test","testNick","");
//
//        String fakeEmail = "servicetest@email.com";
////        ReflectionTestUtils.setField(user, "userId", fakeUserId);
//
//        //mocking
//        given(userRepository.findByUserEmail(fakeEmail)).willReturn(Optional.ofNullable(user));
////        given(userService.existsByUserEmail(fakeEmail)).willThrow(new BaseException());
////        given(userService.existsByUserEmail(any(String.class))).willReturn(false);
//        //when
//        boolean check = userService.existsByUserEmail(fakeEmail);
//
//        //then
//        assertFalse(check);
////        verify(userService).existsByUserEmail(fakeEmail);
//        verify(userRepository).findByUserEmail(fakeEmail);

    }

    @Test
    void findUserById() {
        //given
        User user = new User("servicetest@email.com","TestMan","test","testNick","");

        Long fakeUserId = 1l;
        ReflectionTestUtils.setField(user, "userId", fakeUserId);

        //mocking
        given(userRepository.findById(fakeUserId)).willReturn(Optional.ofNullable(user));

        //when
        Long newUserId = userService.findUserById(fakeUserId).getUserId();

        //then
        assertEquals(fakeUserId, newUserId);
        verify(userRepository).findById(newUserId);
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void findAllUser() {
    }

    @Test
    void login() {
    }

//    @Test
//    @DisplayName("회원 가입")
//    void signUp() {
//        // given
//        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto();
//        userSignupRequestDto.setUserEmail("servicetest@email.com");
//        userSignupRequestDto.setUserPassword("serviceTest");
//        userSignupRequestDto.setUserName("HiTestName");
//        userSignupRequestDto.setUserNickname("testMan");
//        User user = userSignupRequestDto.toEntity();
//
//        Long fakeUserId = 99l;
//        ReflectionTestUtils.setField(user, "userId", fakeUserId);
//
//        //mocking
//        given(userRepository.save(user)).willReturn(user);
//        given(userRepository.findById(fakeUserId)).willReturn(Optional.ofNullable(user));
//
//        //when
//        Long newUserId = userService.signUp(userSignupRequestDto);
//
//        //then
//        User findUser = userRepository.findById(newUserId).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
//
//        assertEquals(user.getUserId(), findUser.getUserId());
//        assertEquals(user.getUsername(), findUser.getUsername());
//        assertEquals(user.getUserNickname(), findUser.getUserNickname());
//
//    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getUserByUserEmail() {
    }

}