package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieRequestDto {
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

    @Builder
    public MovieRequestDto(String movieTitle, int movieRank, String moviePoster, String movieGenres, String movieNation, String movieRunningTime, String movieReleaseDate, String movieDirector, String movieActor, String movieFilmrate, String movieContentBold, String movieContentDetail, String movieContentDetailLong) {
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
        this.movieContentDetailLong = movieContentDetailLong;
    }

    public Movie toEntity() {
        return Movie.builder()
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
                .movieContentDetailLong(movieContentDetailLong)
                .build();
    }
}
