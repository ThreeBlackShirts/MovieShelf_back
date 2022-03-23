package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;

@Getter
public class MovieSearchResponseDto {
    private String movieTitle;
    private String moviePoster;

    public MovieSearchResponseDto(Movie movie) {
        this.movieTitle = movie.getMovieTitle();
        this.moviePoster = movie.getMoviePoster();
    }
}
