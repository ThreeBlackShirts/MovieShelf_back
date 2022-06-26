package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;

@Getter
public class MovieSearchResponseDto {
    private Long movieId;
    private String movieTitle;
    private String moviePoster;

    public MovieSearchResponseDto(Movie movie) {
        this.movieId = movie.getMovieId();
        this.movieTitle = movie.getMovieTitle();
        this.moviePoster = movie.getMoviePoster();
    }
}
