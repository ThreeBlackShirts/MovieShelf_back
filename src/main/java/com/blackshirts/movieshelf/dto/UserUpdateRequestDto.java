package com.blackshirts.movieshelf.dto;


import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String userEmail;
    private String userPassword;
    private String userNickname;
    private String userFilename;

    @Builder
    public UserUpdateRequestDto(String userEmail, String userPassword, String userNickname, String userFilename) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userFilename = userFilename;
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .userFilename(userFilename)
                .build();
    }

}