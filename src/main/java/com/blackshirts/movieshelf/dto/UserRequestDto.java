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

    @Builder
    public UserRequestDto(String userEmail) {
        this.userEmail = getUserEmail();
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .build();
    }
}
