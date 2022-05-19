package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ApiModel(value = "영화", description = "영화 스틸컷 리스트 Class")
@Getter
@Entity(name = "movie_stillcut")
@DynamicUpdate
public class MovieStillcut {

    @ApiModelProperty(value = "스틸컷 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stillcut_id", nullable = false)
    private Long reviewId;

    @ApiModelProperty(value = "movie")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "FK_stillcut_Movie"))
    private Movie movie;

    @ApiModelProperty(value = "스틸컷")
    @Column(name = "movie_stillcut")
    private String stillcut;

    @Builder
    public MovieStillcut(Movie movie, String stillcut) {
        this.movie = movie;
        this.stillcut = stillcut;
    }

    public MovieStillcut() {

    }
}