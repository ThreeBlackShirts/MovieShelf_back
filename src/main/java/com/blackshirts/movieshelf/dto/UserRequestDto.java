package com.blackshirts.movieshelf.dto;


import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String userEmail;
    private String userName;
    private String userNickname;

    @Builder
    public UserRequestDto(String userEmail, String userName, String userNickname) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .build();
    }

}