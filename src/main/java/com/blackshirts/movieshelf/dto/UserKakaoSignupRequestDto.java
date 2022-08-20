package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserKakaoSignupRequestDto {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userNickname;

    private String userKakaoIdentifier;

    @Builder
    public UserKakaoSignupRequestDto(String userEmail, String userName, String userPassword, String userNickname, String userKakaoIdentifier) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userKakaoIdentifier = userKakaoIdentifier;
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .userKakaoIdentifier(userKakaoIdentifier)
                .build();
    }
}
