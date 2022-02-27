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
    private String userPassword;


//    @Builder
//    public UserRequestDto(String userEmail, String userName) {
//        this.userEmail = userEmail;
//        this.userName = userName;
//    }

    @Builder
    public UserRequestDto(String userEmail, String userName, String userPassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPassword(userPassword)
                .build();
    }

}