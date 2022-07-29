package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long reviewId;
    private String writer;
    private String user;
    private String title;
    private String content;
    private int like;

    public ReviewResponseDto(Review entity) {
        this.reviewId = entity.getReviewId();
        this.writer = entity.getUser().getUserEmail();
        this.user = entity.getUser().getUserNickname();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.like = entity.getLikes().size();
    }
}
