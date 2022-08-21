package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.MovieRating;
import lombok.Getter;

@Getter
public class MovieRatingResponseDto {
    private String userEmail;
    private Long movieId;
    private int movieRate;

    public MovieRatingResponseDto(MovieRating movieRating, String userEmail) {
        this.userEmail = userEmail;
        this.movieId = movieRating.getMovieId();
        this.movieRate = movieRating.getMovieRate();
    }
}
