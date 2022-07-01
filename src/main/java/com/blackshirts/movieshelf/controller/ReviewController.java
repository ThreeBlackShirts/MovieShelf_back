package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.*;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.MovieService;
import com.blackshirts.movieshelf.service.ReviewService;
import com.blackshirts.movieshelf.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. Review"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final MovieService movieService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 작성", notes = "영화 후기를 등록합니다.")
    @PostMapping("/review")
    public BaseResponse<Long> create(@ApiParam(value = "영화 후기 등록 정보를 갖는 객체", required = true) @RequestBody ReviewRequestDto requestDto) {
        User user = userService.getUserByUserEmail(requestDto.getUserEmail());
        Movie movie = movieService.getMovieByMovieId(requestDto.getMovieId());
        ReviewCreateRequestDto createRequestDto = new ReviewCreateRequestDto(user, movie, requestDto.getTitle(), requestDto.getContent());
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.create(createRequestDto));
    }

    //전체 조회(목록)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 전체 조회", notes = "영화 전체 후기를 조회합니다.")
    @GetMapping("/review")
    public BaseResponse<List<ReviewListResponseDto>> searchAllDesc() {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.searchAllDesc());
    }

    //개별 조회
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 단건 검색", notes = "reviewId로 영화 후기를 조회합니다.")
    @GetMapping("/review/{reviewId}")
    public BaseResponse<ReviewResponseDto> searchById(@ApiParam(value = "reviewId", required = true) @PathVariable Long reviewId) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.searchById(reviewId));
    }

    //user 개별 조회 - 영화 id별
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 검색", notes = "userEmail별로 영화 후기를 조회합니다.")
    @GetMapping("/review/user/{userEmail}")
    public BaseResponse<List<ReviewResponseDto>> searchByUser(@PathVariable String userEmail) {
        User user = userService.getUserByUserEmail(userEmail);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.searchByUser(user));
    }

    //user 개별 조회
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 검색", notes = "movieId별로 영화 후기를 조회합니다.")
    @GetMapping("/review/movie/{movieId}")
    public BaseResponse<List<ReviewResponseDto>> searchByUser(@PathVariable Long movieId) {
        Movie movie = movieService.getMovieByMovieId(movieId);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.searchByMovie(movie));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 수정", notes = "영화 후기를 수정합니다.")
    @PutMapping("/review/{reviewId}")
    public BaseResponse<Long> update(@ApiParam(value = "reviewId", required = true) @PathVariable Long reviewId, @ApiParam(value = "후기 수정 정보를 갖는 객체", required = true) @RequestBody ReviewUpdateRequestDto requestDto) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.update(reviewId, requestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 후기 삭제", notes = "영화 후기를 삭제합니다.")
    @DeleteMapping("/review/{reviewId}")
    public BaseResponse delete(@ApiParam(value = "reviewId", required = true) @PathVariable Long reviewId){
        reviewService.delete(reviewId);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), BaseResponseCode.OK.getMessage());
    }

}
