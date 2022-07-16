package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.MovieRating;
import lombok.Getter;

@Getter
public class MovieRatingResponseDto {

    private int movieRate;
    private String userEmail;
    private String userNickName;
    private Long movieId;
    private String movieTitle;

    public MovieRatingResponseDto(MovieRating entity) {
        this.movieRate = entity.getMovieRate();
        this.userEmail = entity.getUser().getUserEmail();
        this.userNickName = entity.getUser().getUserNickname();
        this.movieId = entity.getMovie().getMovieId();
        this.movieTitle = entity.getMovie().getMovieTitle();
    }

}
