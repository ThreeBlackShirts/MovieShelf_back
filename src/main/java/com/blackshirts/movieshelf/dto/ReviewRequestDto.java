package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private String userEmail;
    private Long movieId;
    private String title;
    private String content;

    @Builder
    public ReviewRequestDto(String userEmail, Long movieId, String title, String content) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.title = title;
        this.content = content;
    }
}
