package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Review;
import lombok.Getter;

@Getter
public class ReviewListResponseDto {
    private Long reviewId;
    private String user;
    private Long movieId;
    private String title;

    public ReviewListResponseDto(Review entity) {
        this.reviewId = entity.getReviewId();
        this.user = entity.getUser().getUserNickname();
        this.movieId = entity.getMovie().getMovieId();
        this.title = entity.getTitle();
    }
}
