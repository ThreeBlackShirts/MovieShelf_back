package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.dto.UserLoginResponseDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.service.OauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = {"1-2. SocialLogin"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/oauth")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    private static final Logger log = LoggerFactory.getLogger(OauthController.class);

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인")
    @ResponseBody
    @GetMapping("/kakao")
    public BaseResponse<UserLoginResponseDto> kakaoCallback(@ApiParam(value = "kakao auth code", required = true) @RequestParam String code) {
        String accessToken = oauthService.getKaKaoAccessToken(code);
        return new BaseResponse(oauthService.kakaoLogin(accessToken).getStatus(), "요청 성공했습니다.", oauthService.kakaoLogin(accessToken));
    }

}
