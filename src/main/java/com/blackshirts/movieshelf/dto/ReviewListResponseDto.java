package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Review;
import lombok.Getter;

@Getter
public class ReviewListResponseDto {
    private Long reviewId;
    private String userNickName;
    private String userEmail;
    private Long movieId;
    private String movieTitle;
    private String moviePoster;
    private String title;

    public ReviewListResponseDto(Review entity) {
        this.reviewId = entity.getReviewId();
        this.userNickName = entity.getUser().getUserNickname();
        this.userEmail = entity.getUser().getUserEmail();
        this.movieId = entity.getMovie().getMovieId();
        this.movieTitle = entity.getMovie().getMovieTitle();
        this.moviePoster = entity.getMovie().getMoviePoster();
        this.title = entity.getTitle();
    }
}
