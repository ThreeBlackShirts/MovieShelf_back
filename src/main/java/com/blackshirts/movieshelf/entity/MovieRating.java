package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ApiModel(value = "영화 평점", description = "영화(id), 유저(id)와 영화 평점")
@Entity(name = "movies_rating")
@Getter
@NoArgsConstructor
@Table(name = "movies_rating")
public class MovieRating {

    @ApiModelProperty(value = "rate 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long rateId;

    @ApiModelProperty(value = "movie_id")
    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @ApiModelProperty(value = "user_id")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ApiModelProperty(value = "영화 평점")
    @Column(name = "movie_rate", nullable = false)
    private int movieRate;

    public void setMovieRate(int movieRate) {
        this.movieRate = movieRate;
    }

    @Builder
    public MovieRating(Long rateId, Long movieId, Long userId, int movieRate) {
        this.rateId = rateId;
        this.movieId = movieId;
        this.userId = userId;
        this.movieRate = movieRate;
    }

    public MovieRating(Long movieId, Long userId, int movieRate) {
        this.movieId = movieId;
        this.userId = userId;
        this.movieRate = movieRate;
    }
}
