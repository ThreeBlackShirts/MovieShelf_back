package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "회원 영화 리뷰 정보", description = "회원의 영화 리뷰 정보를 가진 Class")
@Entity(name = "reviews")
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

    @ApiModelProperty(value = "아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @ApiModelProperty(value = "user_id")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(name = "user_id", updatable = false, referencedColumnName = "user_id")
    private User user;

    @ApiModelProperty(value = "제목")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(value = "내용")
    @Column(name = "content", length = 4000)
    private String content;

    @OneToMany(mappedBy = "review", orphanRemoval=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Like> likes = new HashSet<>();

    @ApiModelProperty(value = "movie_id")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Movie.class)
    @JoinColumn(name = "movie_id", updatable = false, referencedColumnName = "movie_id")
    private Movie movie;

    //빌더
    @Builder
    public Review(User user, Movie movie, String title, String content) {
        this.user = user;
        this.movie = movie;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
