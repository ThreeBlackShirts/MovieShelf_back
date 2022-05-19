package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieStillcut;
import com.blackshirts.movieshelf.entity.MovieTrailer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieRequestDto {
    private Long movieId;
    private String movieTitle;
    private String moviePoster;
    private String movieGenres;
    private String movieNation;
    private String movieDirector;
    private String movieActor;
    private String movieFilmrate;
    private String movieRunningTime;
    private String movieReleaseDate;
    private String movieContentBold;
    private String movieContentDetail;
    private String movieContentDetailLong;
    private int movieRank;
    private List<MovieStillcut> movieStillcut = new ArrayList<>();
    private List<MovieTrailer> movieTrailer = new ArrayList<>();

    @Builder
    public MovieRequestDto(Long movieId, String movieTitle, int movieRank, String moviePoster, String movieGenres, String movieNation, String movieRunningTime,
                           String movieReleaseDate, String movieDirector, String movieActor, String movieFilmrate, String movieContentBold,
                           String movieContentDetail, List<MovieStillcut> movieStillcut, List<MovieTrailer> movieTrailer) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRank = movieRank;
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
        this.movieStillcut = movieStillcut;
        this.movieTrailer = movieTrailer;
    }

    public Movie toEntity() {
        return Movie.builder()
                .movieId(movieId)
                .movieTitle(movieTitle)
                .movieRank(movieRank)
                .moviePoster(moviePoster)
                .movieGenres(movieGenres)
                .movieNation(movieNation)
                .movieRunningTime(movieRunningTime)
                .movieReleaseDate(movieReleaseDate)
                .movieDirector(movieDirector)
                .movieActor(movieActor)
                .movieFilmrate(movieFilmrate)
                .movieContentBold(movieContentBold)
                .movieContentDetail(movieContentDetail)
                .stillcuts(movieStillcut)
                .trailers(movieTrailer)
                .build();
    }
}
