package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

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
    @JoinColumn
//    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "FK_MovieRate_Movie"))
//     @JoinColumn(name = "movie_id")  name : 내 엔티티에서 외래키 칼럼명을 만들어주는 설정
    private Movie movie;

    @ApiModelProperty(value = "user_email")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn
    private User user;

    @ApiModelProperty(value = "영화 아이디")
    @Column(name = "movie_rate", nullable = false, unique = true)
    private int movieRate;

    @Builder
    public MovieRating(Long rateId, Movie movie, User user, int movieRate) {
        this.rateId = rateId;
        this.movie = movie;
        this.user = user;
        this.movieRate = movieRate;
    }

    public MovieRating(Movie movie, User user, int movieRate) {
        this.movie = movie;
        this.user = user;
        this.movieRate = movieRate;
    }
}
