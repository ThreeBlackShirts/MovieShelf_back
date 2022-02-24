package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.dto.UserResponseDto;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long saveUser(UserRequestDto userRequestDto) {
        userRepository.save(userRequestDto.toEntity());
        return userRepository.findByUserEmail(userRequestDto.getUserEmail()).getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserByEmail(String email) {
        User user = userRepository.findByUserEmail(email);
        if (user == null) throw new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다.");
        else return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public Long update(Long id, UserRequestDto userRequestDto){
        User newUser = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        newUser.updateUserNickname(userRequestDto.getUserName());
        return id;
    }

    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }

//    public Optional<Boolean> existsUser(String email) {
//        return userRepository.existsByEmail(email);
//    }

}
