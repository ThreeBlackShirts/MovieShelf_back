package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@ApiModel(value = "영화 별점", description = "영화 아이디와  Class")
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ApiModelProperty(value = "user_email")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(name = "user_email", updatable = false, referencedColumnName = "user_email")
    private User user;

    @ApiModelProperty(value = "영화 아이디")
    @Column(name = "movie_id", nullable = false, unique = true)
    private int movieRate;

    @Builder
    public MovieRating(Long rateId, Movie movie, User user, int movieRate) {
        this.rateId = rateId;
        this.movie = movie;
        this.user = user;
        this.movieRate = movieRate;
    }
}
