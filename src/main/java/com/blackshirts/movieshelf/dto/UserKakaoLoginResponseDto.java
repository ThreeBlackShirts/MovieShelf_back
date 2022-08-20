package com.blackshirts.movieshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserKakaoLoginResponseDto {
    private HttpStatus status;
    private String token;
    private String userEmail;
}
