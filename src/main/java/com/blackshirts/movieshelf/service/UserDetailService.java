package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.UserResponseDto;
import com.blackshirts.movieshelf.entity.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blackshirts.movieshelf.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserResponseDto> userOptional = Optional.ofNullable(userService.findUserByEmail(username));

        UserResponseDto userResponseDto = userOptional.orElseThrow(()->new UsernameNotFoundException("user name not found!"));
        return new org.springframework.security.core.userdetails.User(userResponseDto.getUserEmail(),userResponseDto.getUserPassword(),new ArrayList<>());
    }
}
