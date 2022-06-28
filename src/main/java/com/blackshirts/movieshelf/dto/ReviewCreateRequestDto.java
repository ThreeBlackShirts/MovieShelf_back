package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
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
    private Movie movie;
    private String title;
    private String content;

    @Builder
    public ReviewCreateRequestDto(User user, Movie movie, String title, String content) {
        this.user = user;
        this.movie = movie;
        this.title = title;
        this.content = content;
    }

    public Review toEntity() {
        return Review.builder()
                .user(user)
                .movie(movie)
                .title(title)
                .content(content)
                .build();
    }
}
