package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ApiModel(value = "영화", description = "영화 트레일러 리스트 Class")
@Getter
@Entity(name = "movie_tralier")
@DynamicUpdate
public class MovieTrailer {
    @ApiModelProperty(value = "트레일러 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tralier_id", nullable = false)
    private Long reviewId;

    @ApiModelProperty(value = "movie")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "FK_tralier_Movie"))
    private Movie movie;

    @ApiModelProperty(value = "트레일러")
    @Column(name = "movie_tralier", length = 500)
    private String tralier;

    @Builder
    public MovieTrailer(Movie movie, String tralier) {
        this.movie = movie;
        this.tralier = tralier;
    }

    public MovieTrailer() {

    }
}
