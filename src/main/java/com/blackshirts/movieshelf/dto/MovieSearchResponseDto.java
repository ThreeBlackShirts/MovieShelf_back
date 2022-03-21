package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.SearchMovie;

public class MovieSearchResponseDto {
    private String movieTitle;
    private String moviePoster;

    public MovieSearchResponseDto(SearchMovie SearchMovie) {
        this.movieTitle = SearchMovie.getMovieTitle();
        this.moviePoster = SearchMovie.getMoviePoster();
    }
}
