package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserResponseDto;
import com.blackshirts.movieshelf.exception.ListResult;
import com.blackshirts.movieshelf.service.ResponseService;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @PostMapping("/register")
    public Long save(@RequestParam String email, @RequestParam String name) throws Exception {

//
//        try {
//            userService.existsUser(user.get("email"))
//            user.get("password") = passwordEncoder.encode(user.get("password"));
//        } catch (Exception e) {
//            return ResponseEntity.
//        }
//        return ResponseEntity.ok(userService.createUser(user));
        return null;
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ListResult<UserResponseDto> findAllUsers() {
        return responseService.getListResult(userService.findAllUser());
    }
}
