package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Getter;

@Getter
public class MovieResponseDto {
    private Long movieId;
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
        this.movieId = movie.getMovieId();
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

    public MovieResponseDto(Long movieId, int movieRank, String movieTitle, String moviePoster, String movieGenres, String movieNation, String movieRunningTime, String movieReleaseDate, String movieDirector, String movieActor, String movieFilmrate, String movieContentBold, String movieContentDetail) {
        this.movieId = movieId;
        this.movieRank = movieRank;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieGenres = movieGenres;
        this.movieNation = movieNation;
        this.movieRunningTime = movieRunningTime;
        this.movieReleaseDate = movieReleaseDate;
        this.movieDirector = movieDirector;
        this.movieActor = movieActor;
        this.movieFilmrate = movieFilmrate;
        this.movieContentBold = movieContentBold;
        this.movieContentDetail = movieContentDetail;
    }
}
