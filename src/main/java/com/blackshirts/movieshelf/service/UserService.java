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


    public Optional<User> findUser(String email) throws Exception {
        return userRepository.findByEmail(email);
    }

    public Boolean existsUser(String email) throws Exception {
        return userRepository.existsByEmail(email);
    }

//    public


}
