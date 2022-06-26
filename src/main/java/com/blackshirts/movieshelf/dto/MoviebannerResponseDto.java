package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoviebannerResponseDto {
    private String movieTitle;
    private String movieStillcut;
    private String movieContentBold;
    private String movieContentDetail;

    public MoviebannerResponseDto(Movie movie, String movieStillcut) {
        this.movieTitle = movie.getMovieTitle();
        this.movieStillcut = movieStillcut;
        this.movieContentBold = movie.getMovieContentBold();
        this.movieContentDetail = movie.getMovieContentDetail();
    }
}
