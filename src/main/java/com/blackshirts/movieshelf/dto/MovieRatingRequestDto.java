package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieRating;
import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieRatingRequestDto {
    private String userEmail;
    private Long movieId;
    private int movieRate;

    @Builder
    public MovieRatingRequestDto(String userEmail, Long movieId, int movieRate) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.movieRate = movieRate;
    }

}
