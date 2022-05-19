package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;

@Getter
public class MovieResponseDto {
    private int movieRank;
    private String movieTitle;
    private String moviePoster;
    private String movieGenres;
    private String movieNation;
    private String movieRunningTime;
    private String movieReleaseDate;
    private String movieDirector;
    private String movieActor;
    private String movieFilmrate;
    private String movieContentBold;
    private String movieContentDetail;

    public MovieResponseDto(Movie movie){
        this.movieRank = movie.getMovieRank();
        this.movieTitle = movie.getMovieTitle();
        this.moviePoster = movie.getMoviePoster();
        this.movieGenres = movie.getMovieGenres();
        this.movieNation = movie.getMovieNation();
        this.movieRunningTime = movie.getMovieRunningTime();
        this.movieReleaseDate = movie.getMovieReleaseDate();
        this.movieDirector = movie.getMovieDirector();
        this.movieActor = movie.getMovieActor();
        this.movieFilmrate = movie.getMovieFilmrate();
        this.movieContentBold = movie.getMovieContentBold();
        this.movieContentDetail = movie.getMovieContentDetail();
    }

    public MovieResponseDto(int movieRank, String movieTitle, String moviePoster, String movieGenres, String movieNation, String movieRunningTime, String movieReleaseDate, String movieDirector, String movieActor, String movieFilmrate, String movieContentBold, String movieContentDetail) {
    }
}
