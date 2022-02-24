package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

//    public Optional<Boolean> existsUser(String email) {
//        return userRepository.existsByEmail(email);
//    }

//    public


}
