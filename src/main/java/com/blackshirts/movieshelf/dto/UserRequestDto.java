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

    private Long userId;
    private String userNickname;
    private String userFilename;

    @Builder
    public UserRequestDto(Long userId, String userNickname, String userFilename) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userFilename = userFilename;
    }

    // dto -> entity
    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userNickname(userNickname)
                .userFilename(userFilename)
                .build();
    }

}