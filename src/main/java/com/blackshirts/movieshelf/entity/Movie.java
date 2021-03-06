package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "영화", description = "영화 제목, 포스터url, 줄거리 등을 가진 Class")
@Getter
@Setter
@Entity(name = "movies")
@DynamicUpdate //update 시 null인 필드 제외
public class Movie {

    @ApiModelProperty(value = "영화 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @ApiModelProperty(value = "영화제목")
    @Column(name = "movie_title", length = 50, nullable = false, unique = true)
    private String movieTitle;

    @ApiModelProperty(value = "영화평점")
    @Column(name = "movie_rate", length = 100)
    @ColumnDefault("0")
    private double movieRate;

    @ApiModelProperty(value = "포스터url")
    @Column(name = "movie_poster")
    private String moviePoster;

    @ApiModelProperty(value = "장르 리스트")
    @Column(name = "movie_genres")
    private String movieGenres;

    @ApiModelProperty(value = "제조국")
    @Column(name = "movie_nation")
    private String movieNation;

    @ApiModelProperty(value = "상영시간")
    @Column(name = "movie_running_time")
    private String movieRunningTime;

    @ApiModelProperty(value = "개봉일")
    @Column(name = "movie_release_date")
    private String movieReleaseDate;

    @ApiModelProperty(value = "감독")
    @Column(name = "movie_director")
    private String movieDirector;

    @ApiModelProperty(value = "출연")
    @Column(name = "movie_actor")
    private String movieActor;

    @ApiModelProperty(value = "연령등급")
    @Column(name = "movie_filmrate")
    private String movieFilmrate;

    @ApiModelProperty(value = "한줄 줄거리")
    @Column(name = "movie_content_bold")
    private String movieContentBold;

    @ApiModelProperty(value = "자세한 줄거리")
    @Column(name = "movie_content_detail", length = 800)
    private String movieContentDetail;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    List<MovieStillcut> stillcuts = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    List<MovieTrailer> trailers = new ArrayList<>();

    @Builder
    public Movie(Long movieId, String movieTitle, Double movieRate, String moviePoster, String movieGenres,
                 String movieNation, String movieRunningTime, String movieReleaseDate, String movieDirector,
                 String movieActor, String movieFilmrate, String movieContentBold, String movieContentDetail,
                 List<MovieStillcut> stillcuts, List<MovieTrailer> trailers) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.moviePoster = moviePoster;
        this.movieGenres = movieGenres;
        this.movieNation = movieNation;
        this.movieDirector = movieDirector;
        this.movieActor = movieActor;
        this.movieFilmrate = movieFilmrate;
        this.movieRunningTime = movieRunningTime;
        this.movieReleaseDate = movieReleaseDate;
        this.movieContentBold = movieContentBold;
        this.movieContentDetail = movieContentDetail;
        this.stillcuts = stillcuts;
        this.trailers = trailers;
    }

    public Movie() {

    }

    public String getMovieTitle() {
        return this.movieTitle;
    }
    public String getMoviePoster() {
        return this.moviePoster;
    }
}
