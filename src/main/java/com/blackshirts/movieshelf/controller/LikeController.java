package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.entity.WishList;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.LikeService;
import com.blackshirts.movieshelf.service.ReviewService;
import com.blackshirts.movieshelf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"4. Like"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/like")
public class LikeController {
    private final LikeService likeService;
    private static final Logger log = LoggerFactory.getLogger(LikeController.class);

    private final UserService userService;

    @PostMapping("/{reviewId}")
    public BaseResponse addLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        log.info(userRequestDto.getUserEmail());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.addLike(userRequestDto.toEntity(), reviewId));

    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse deleteLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.delete(userRequestDto.toEntity(), reviewId));
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
        User user = userService.getUserByUserEmail(userEmail);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.searchByUser(user));
    }

}
