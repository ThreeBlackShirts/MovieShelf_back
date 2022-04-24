package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewCreateRequestDto {
    private User user;
    private String title;
    private String content;

    @Builder
    public ReviewCreateRequestDto(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public Review toEntity() {
        return Review.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
