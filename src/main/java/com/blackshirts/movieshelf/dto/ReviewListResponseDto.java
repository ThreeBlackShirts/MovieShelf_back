package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Review;
import lombok.Getter;

@Getter
public class ReviewListResponseDto {
    private Long reviewId;
    private String user;
    private Long movieId;
    private String movieTitle;
    private String moviePoster;
    private String title;

    public ReviewListResponseDto(Review entity) {
        this.reviewId = entity.getReviewId();
        this.user = entity.getUser().getUserNickname();
        this.movieId = entity.getMovie().getMovieId();
        this.movieTitle = entity.getMovie().getMovieTitle();
        this.moviePoster = entity.getMovie().getMoviePoster();
        this.title = entity.getTitle();
    }
}
