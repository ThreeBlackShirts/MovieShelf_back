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

    @Builder
    public UserRequestDto(String userEmail, String userName){
        this.userEmail = userEmail;
        this.userName = userName;
    }

//    public User toEntity() {
//        return User.builder()
//                .
//    }


}