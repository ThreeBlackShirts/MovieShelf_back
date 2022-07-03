package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.WishList;
import lombok.Getter;

@Getter
public class WishListResponseDto {
    private Long movieId;
    private String userEmail;

    public WishListResponseDto(WishList entity) {
        this.userEmail = entity.getUser().getUserEmail();
        this.movieId = entity.getMovie().getMovieId();
    }
}
