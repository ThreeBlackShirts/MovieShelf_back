package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieStillcut;
import com.blackshirts.movieshelf.entity.MovieTrailer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieDetailResponseDto {
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
    private List<String> movieStillcut;
    private List<List<String>> movieTrailer;

    public MovieDetailResponseDto(Movie movie, MovieStillcut movieStillcut, MovieTrailer movieTrailer){
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
        this.movieStillcut.add(movieStillcut.getStillcut());
        this.movieTrailer.add(Arrays.asList(movieTrailer.getTralierTitle(), movieTrailer.getTralierImg(), movieTrailer.getTralier()));
    }
}
