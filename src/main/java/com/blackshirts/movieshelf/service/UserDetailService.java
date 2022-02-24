package com.blackshirts.movieshelf.service;

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
        Optional<User> userOptional = userService.findUserByEmail(username);

        User user = userOptional.orElseThrow(()->new UsernameNotFoundException("user name not found!"));
        return new org.springframework.security.core.userdetails.User(user.getUserEmail(),user.getPassword(),new ArrayList<>());
    }
}
