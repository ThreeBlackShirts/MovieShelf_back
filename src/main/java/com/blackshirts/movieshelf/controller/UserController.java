package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/register")
    public Long register(@RequestBody Map<String, String> user) throws Exception {

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
}
