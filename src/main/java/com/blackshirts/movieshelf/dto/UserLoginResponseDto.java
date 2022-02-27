package com.blackshirts.movieshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private HttpStatus status;
    private String token;
}
