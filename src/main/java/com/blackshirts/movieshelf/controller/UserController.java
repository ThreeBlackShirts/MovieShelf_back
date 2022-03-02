package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.*;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
    @PostMapping("/register")
    public BaseResponse<Long> save(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserRequestDto userRequestDto) throws Exception {
        return new BaseResponse<>(HttpStatus.OK, "회원을 등록합니다.", userService.saveUser(userRequestDto));
    }

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserLoginRequestDto userLoginDto) throws Exception {
        if (!userService.existsByUserEmail(userLoginDto.getUserEmail())) {
            throw new BaseException(BaseResponseCode.USER_NOT_FOUND);
        }

        UserResponseDto user = userService.findUserByEmail(userLoginDto.getUserEmail());

        if (!passwordEncoder.matches(userLoginDto.getUserPassword(), user.getUserPassword())) {
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);
        }

        return new BaseResponse(userService.login(userLoginDto).getStatus(), "요청 성공했습니다.", userService.login(userLoginDto).getToken());
    }


    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public BaseResponse<List<UserResponseDto>> findAllUsers() {
        return new BaseResponse(HttpStatus.OK, "All Users", userService.findAllUser());
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public BaseResponse<Long> signup(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserSignupRequestDto userSignupRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.signUp(userSignupRequestDto));
    }

}
