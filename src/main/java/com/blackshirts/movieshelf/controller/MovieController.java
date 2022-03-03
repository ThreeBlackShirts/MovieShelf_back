package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.exception.CommonResult;
import com.blackshirts.movieshelf.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. Movie"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @ApiOperation(value = "크롤링 데이터 등록", notes = "크롤링 된 영화 정보를 등록합니다.")
    @PostMapping("/moviedatainsert")
    public CommonResult<String> movieSave() throws Exception {
        return new CommonResult(HttpStatus.OK, "크롤링 된 영화 정보가 등록되었습니다.",
                "크롤링 데이터");
    }
}
