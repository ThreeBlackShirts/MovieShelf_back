package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.*;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.exception.EmailLoginFailedCException;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

//    @Transactional(readOnly = true)
//    public Long saveUser(UserRequestDto userRequestDto) {
//        userRequestDto.setUserPassword(passwordEncoder.encode(userRequestDto.getUserPassword()));
//        userRepository.save(userRequestDto.toEntity());
//        return userRepository.findByUserEmail(userRequestDto.getUserEmail()).get().getUserId();
//    }

    @Transactional(readOnly = true)
    public boolean existsByUserEmail(String email) {
        return userRepository.existsByUserEmail(email).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_EMAIL));
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserByEmail(String email) {
        User user = userRepository.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByUserEmail(userLoginRequestDto.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getPassword()))
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);

        String token = jwtTokenProvider.createToken(userLoginRequestDto.getUserEmail());
        return new UserLoginResponseDto(HttpStatus.OK, token);
    }

//    public Long update(Long id, UserRequestDto userRequestDto){
//        User newUser = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
//        newUser.updateUserNickname(userRequestDto.getUserName());
//        return id;
//    }

    @Transactional(readOnly = true)
    public Long signUp(UserSignupRequestDto userSignupRequestDto) throws BaseException {

        boolean exitsUserCheck = userRepository.existsByUserEmail(userSignupRequestDto.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.BAD_REQUEST));
        if (exitsUserCheck) {
            throw new BaseException(BaseResponseCode.DUPLICATE_EMAIL);
        }
        Long id;
        try {
            id = userRepository.save(userSignupRequestDto.toEntity()).getUserId();
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_USER);
        }
        return id;
    }

    public Optional<User> update(UserRequestDto userRequestDto) throws BaseException {
        Optional<User> user = userRepository.findById(userRequestDto.getUserId());
        try {
            user.ifPresent(selectUser -> {
                selectUser.setUserNickname(userRequestDto.getUserNickname());
                selectUser.setUserFilename(userRequestDto.getUserFilename());
                userRepository.save(selectUser);
            });
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return user;
    }

    public Long delete(Long id) throws BaseException {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.USER_NOT_FOUND);
        }
        return id;
    }


    public User getUserByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

//    public Optional<Boolean> existsUser(String email) {
//        return userRepository.existsByEmail(email);
//    }

}
