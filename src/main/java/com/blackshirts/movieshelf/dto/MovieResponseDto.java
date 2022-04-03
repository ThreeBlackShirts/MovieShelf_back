package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;

import java.util.Set;

@Getter
public class MovieResponseDto {
    private int movieRank;
    private String movieTitle;
    private String moviePoster;
    private String movieGenres;
    private String movieNation;
    private String movieRunningTime;
    private String movieReleaseDate;
    private String movieContentBold;
    private String movieContentDetail;
    private String movieContentDetailLong;

    public MovieResponseDto(Movie movie){
        this.movieRank = movie.getMovieRank();
        this.movieTitle = movie.getMovieTitle();
        this.moviePoster = movie.getMoviePoster();
        this.movieGenres = movie.getMovieGenres();
        this.movieNation = movie.getMovieNation();
        this.movieRunningTime = movie.getMovieRunningTime();
        this.movieReleaseDate = movie.getMovieReleaseDate();
        this.movieContentBold = movie.getMovieContentBold();
        this.movieContentDetail = movie.getMovieContentDetail();
        this.movieContentDetailLong = movie.getMovieContentDetailLong();
    }

    public MovieResponseDto(int movieRank, String movieTitle, String moviePoster, String movieGenres, String movieNation, String movieRunningTime, String movieReleaseDate, String movieContentBold, String movieContentDetail, String movieContentDetailLong) {
    }
}
