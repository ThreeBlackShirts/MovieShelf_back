package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.service.OauthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/oauth")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        String accessToken = oauthService.getKaKaoAccessToken(code);
        System.out.println(accessToken);
    }
}
