package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.LikeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api(tags = {"Like"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/like")
public class LikeController {
    private final LikeService likeService;
    private static final Logger log = LoggerFactory.getLogger(LikeController.class);

    @PostMapping("/{reviewId}")
    public BaseResponse addLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        log.info(userRequestDto.getUserEmail());

        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.addLike(userRequestDto.toEntity(), reviewId));

    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse deleteLike(@RequestBody UserRequestDto userRequestDto, @PathVariable Long reviewId) {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.delete(userRequestDto.toEntity(), reviewId));
    }
}
