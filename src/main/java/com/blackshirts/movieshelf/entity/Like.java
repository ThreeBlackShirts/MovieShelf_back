package com.blackshirts.movieshelf.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "likes")
@Entity
public class Like {

    @ApiModelProperty(value = "아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ApiModelProperty(value = "review")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "FK_Like_Review"))
    private Review review;

    @ApiModelProperty(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_Like_User"))
    private User user;

    public Like(Review review, User user) {
        this.review = review;
        this.user = user;
    }
}