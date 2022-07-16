package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.MovieRatingRequestDto;
import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.LikeService;
import com.blackshirts.movieshelf.service.MovieRatingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2-1. MovieRating"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/movierate")
public class MovieRatingController {

    private final MovieRatingService movieRatingService;

    private static final Logger log = LoggerFactory.getLogger(MovieRatingController.class);

//    @PostMapping("/{movieId}")
//    public BaseResponse addLike(@RequestBody MovieRatingRequestDto movieRatingRequestDto, @PathVariable Long reviewId) {
//        log.info(movieRatingRequestDto.getMovieRate());
//
//        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.create(movieRatingRequestDto.toEntity(), reviewId));
//
//    }
//
//    @DeleteMapping("/{reviewId}")
//    public BaseResponse deleteLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
//        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.delete(userRequestDto.toEntity(), reviewId));
//    }
}
