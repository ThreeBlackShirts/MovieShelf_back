package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.MovieRatingRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.MovieRatingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2-1. MovieRating"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/movie/rate")
public class MovieRatingController {

    private final MovieRatingService movieRatingService;

    private static final Logger log = LoggerFactory.getLogger(MovieRatingController.class);

    @PostMapping("/add")
    public BaseResponse addMovieRate(@RequestBody MovieRatingRequestDto movieRatingRequestDto) {
        log.info("addMovieRate() " + movieRatingRequestDto.getMovieRate());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.create(movieRatingRequestDto));

    }

    @PostMapping("/update")
    public BaseResponse updateMovieRate(@RequestBody MovieRatingRequestDto movieRatingRequestDto) {
        log.info("updateMovieRate() " + movieRatingRequestDto.getMovieRate());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.updateMovieRate(movieRatingRequestDto));

    }

}
