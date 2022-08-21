package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.MovieRatingRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.MovieRatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "영화 평점 조회", notes = "MovieRatingRequestDto로 영화 평점 조회")
    @PostMapping("/find")
    public BaseResponse findMovieRate(@RequestBody MovieRatingRequestDto movieRatingRequestDto) {
        log.info("findMovieRate() " + movieRatingRequestDto.getMovieRate());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.findMovieRate(movieRatingRequestDto));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 평점 추가", notes = "MovieRatingRequestDto로 영화 평점 추가")
    @PostMapping("/add")
    public BaseResponse addMovieRate(@RequestBody MovieRatingRequestDto movieRatingRequestDto) {
        log.info("addMovieRate() " + movieRatingRequestDto.getMovieRate());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.create(movieRatingRequestDto));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 평점 수정", notes = "MovieRatingRequestDto로 영화 평점 수정")
    @PostMapping("/update")
    public BaseResponse updateMovieRate(@RequestBody MovieRatingRequestDto movieRatingRequestDto) {
        log.info("updateMovieRate() " + movieRatingRequestDto.getMovieRate());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.updateMovieRate(movieRatingRequestDto));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 평점 삭제", notes = "MovieRatingRequestDto로 영화 평점 삭제")
    @PostMapping("/delete")
    public BaseResponse deleteMovieRate(@RequestBody MovieRatingRequestDto movieRatingRequestDto) {
        log.info("deleteMovieRate() " + movieRatingRequestDto.getMovieRate());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), movieRatingService.deleteMovieRate(movieRatingRequestDto));

    }

}
