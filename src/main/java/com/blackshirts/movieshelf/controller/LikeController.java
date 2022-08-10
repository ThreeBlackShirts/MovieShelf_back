package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = {"4. Like"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v4/like")
public class LikeController {
    private final LikeService likeService;
    private static final Logger log = LoggerFactory.getLogger(LikeController.class);

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "좋아요 단건 조회", notes = "userEmail, reviewId로 좋아요 단건 조회")
    @GetMapping("/{reviewId}")
    public BaseResponse findLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.findLike(userRequestDto, reviewId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "좋아요 존재 조회", notes = "userEmail, reviewId로 좋아요 존재 조회 / True면 존재")
    @PostMapping("/validate/{reviewId}")
    public BaseResponse validateWishList(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.validateLike(userRequestDto, reviewId));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "좋아요 등록", notes = "userEmail, reviewId로 좋아요 등록")
    @PostMapping("/{reviewId}")
    public BaseResponse addLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        log.info("addLike() " + new Date());
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.addLike(userRequestDto, reviewId));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "좋아요 삭제", notes = "reviewId로 좋아요 삭제")
    @DeleteMapping("/{reviewId}")
    public BaseResponse deleteLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        log.info("deleteLike() " + new Date());
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.delete(userRequestDto, reviewId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "좋아요 누른 리뷰 검색", notes = "userEmail별로 좋아요한 리뷰를 조회합니다.")
    @GetMapping("/list/{userEmail}")
    public BaseResponse<List<Like>> searchByUser(@PathVariable String userEmail) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.searchByUser(userEmail));
    }

}
