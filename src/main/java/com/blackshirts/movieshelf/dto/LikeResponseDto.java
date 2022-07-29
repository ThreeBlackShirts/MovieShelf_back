package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.WishList;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    private Long movieId;
    private String movieTitle;
    private String userEmail;
    private Long reviewId;
    private String reviewTitle;

    public LikeResponseDto(Like entity) {
        this.userEmail = entity.getUser().getUserEmail();
        this.movieId = entity.getReview().getMovie().getMovieId();
        this.movieTitle = entity.getReview().getMovie().getMovieTitle();
        this.reviewId = entity.getReview().getReviewId();
        this.reviewTitle = entity.getReview().getTitle();
    }
}
