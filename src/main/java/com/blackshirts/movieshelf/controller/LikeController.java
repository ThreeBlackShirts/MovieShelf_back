package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.service.LikeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Like"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like/{reviewId}")
    public BaseResponse<String> addLike(UserRequestDto userRequestDto, @PathVariable Long reviewId) {

        boolean result = false;


        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), likeService.addLike(userRequestDto.toEntity(), reviewId));

    }

}
