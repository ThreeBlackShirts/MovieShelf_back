package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.LikeService;
import com.blackshirts.movieshelf.service.WishListService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"WishList"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v4/wish")
public class WishListController {
    private final WishListService wishListService;
    private static final Logger log = LoggerFactory.getLogger(WishListController.class);

    @PostMapping("/{movieId}")
    public BaseResponse<Long> addLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long movieId) {
        log.info(userRequestDto.getUserEmail());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), wishListService.createWish(userRequestDto.toEntity(), movieId));

    }

    @DeleteMapping("/{movieId}")
    public BaseResponse<Long> deleteLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long movieId) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), wishListService.delete(userRequestDto.toEntity(), movieId));
    }
}
