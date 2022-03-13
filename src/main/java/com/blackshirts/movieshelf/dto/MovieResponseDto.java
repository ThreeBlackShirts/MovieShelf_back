package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;

@Getter
public class MovieResponseDto {
    private int movieRank;
    private String movieTitle;
    private String moviePoster;
    private String movieContentBold;
    private String movieContentDetail;
    private String movieContentDetailLong;

    public MovieResponseDto(Movie movie){
        this.movieRank = movie.getMovieRank();
        this.movieTitle = movie.getMovieTitle();
        this.moviePoster = movie.getMoviePoster();
        this.movieContentBold = movie.getMovieContentBold();
        this.movieContentDetail = movie.getMovieContentDetail();
        this.movieContentDetailLong = movie.getMovieContentDetailLong();
    }

    public MovieResponseDto(int movieRank, String movieTitle, String moviePoster, String movieContentBold, String movieContentDetail, String movieContentDetailLong) {
    }
}
