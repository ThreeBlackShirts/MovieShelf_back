package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@ApiModel(value = "영화", description = "영화 제목, 포스터url, 줄거리 등을 가진 Class")
@Getter
@Entity(name = "movies")
public class Movie {

    @ApiModelProperty(value = "영화 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @ApiModelProperty(value = "영화제목")
    @Column(name = "movie_title", length = 50, nullable = false, unique = true)
    private String movieTitle;

    @ApiModelProperty(value = "포스터url")
    @Column(name = "movie_poster", nullable = false)
    private String moviePoster;

    @ApiModelProperty(value = "한줄 줄거리")
    @Column(name = "movie_content_bold", nullable = false)
    private String movieContentBold;

    @ApiModelProperty(value = "자세한 줄거리")
    @Column(name = "movie_content_detail", nullable = false)
    private String movieContentDetail;

    @ApiModelProperty(value = "영화순위")
    @Column(name = "movie_rank", length = 100, nullable = false)
    private int movieRank;


    @Builder
    public Movie(String movieTitle, String moviePoster, String movieContentBold, String movieContentDetail, int movieRank) {
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieContentBold = movieContentBold;
        this.movieContentDetail = movieContentDetail;
        this.movieRank = movieRank;
    }

    public Movie() {

    }
}
