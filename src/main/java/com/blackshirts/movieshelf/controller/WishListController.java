package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.entity.WishList;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.MovieService;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.service.WishListService;
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

@Api(tags = {"5. WishList"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v4/wish")
public class WishListController {
    private final WishListService wishListService;
    private final UserService userService;

    private final MovieService movieService;
    private static final Logger log = LoggerFactory.getLogger(WishListController.class);

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/{movieId}")
    public BaseResponse<Long> addLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long movieId) {

        log.info("addLike()" + new Date());

        Long wishListId = wishListService.createWish(userRequestDto, movieId);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), wishListId);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/{movieId}")
    public BaseResponse<Long> deleteLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long movieId) {

        Long deletedWishId = wishListService.delete(userRequestDto, movieId);
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), deletedWishId);
    }

    //user 개별 조회 - 영화 id별
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "영화 위시리스트 검색", notes = "userEmail별로 영화 위시리스트를 조회합니다.")
    @GetMapping("/list/{userEmail}")
    public BaseResponse<List<WishList>> searchByUser(@PathVariable String userEmail) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), wishListService.searchByUser(userEmail));
    }
}
