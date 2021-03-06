package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.*;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

//    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
//    @PostMapping("/register")
//    public BaseResponse<Long> save(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserUpdateRequestDto userRequestDto) throws Exception {
//        return new BaseResponse<>(HttpStatus.OK, "회원을 등록합니다.", userService.saveUser(userRequestDto));
//    }

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserLoginRequestDto userLoginDto) throws Exception {

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

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/id/{userId}")
    public BaseResponse<UserResponseDto> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId) {
        return new BaseResponse<>(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.findUserById(userId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "userEmail로 회원을 조회합니다.")
    @GetMapping("/user/email/{userEmail}")
    public BaseResponse<UserResponseDto> findUserByEmail(@ApiParam(value = "회원 이메일", required = true) @PathVariable String userEmail) {
        return new BaseResponse<>(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.findUserByEmail(userEmail));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정합니다.")
    @PutMapping("/user")
    public BaseResponse<Long> update(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserUpdateRequestDto userRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.update(userRequestDto).get());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public BaseResponse delete(@ApiParam(value = "회원 Id", required = true) @PathVariable Long userId) {
        userService.delete(userId);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), BaseResponseCode.OK.getMessage());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/userEmail/{userEmail}")
    public BaseResponse deleteByEmail(@ApiParam(value = "회원 Email", required = true) @PathVariable String userEmail) {
        userService.deleteByEmail(userEmail);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), BaseResponseCode.OK.getMessage());
    }


}
