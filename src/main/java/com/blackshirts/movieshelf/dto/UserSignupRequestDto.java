package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userNickname;

    @Builder
    public UserSignupRequestDto(String userEmail, String userName, String userPassword, String userNickname) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .build();
    }
}
