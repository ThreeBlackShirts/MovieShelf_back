package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String userEmail;
    private final String userName;
    private final String userNickname;
    private final String userFilename;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUsername();
        this.userNickname = user.getUserNickname();
        this.userFilename = user.getUserFilename();
    }
}
