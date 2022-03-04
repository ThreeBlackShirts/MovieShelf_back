package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.MovieResponseDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"2. Movie"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @ApiOperation(value = "크롤링 데이터 등록", notes = "크롤링 된 영화 정보를 등록합니다.")
    @GetMapping("/moviedatainsert")
    public BaseResponse<String> movieSave() throws Exception {
        movieService.NavermovieCrawling();
        return new BaseResponse(HttpStatus.OK, "크롤링 된 영화 정보가 등록되었습니다.",
                "크롤링 데이터");
    }

    @ApiOperation(value = "영화 목록 조회", notes = "등록된 모든 영화를 조회합니다.")
    @GetMapping("/movielist")
    public BaseResponse<List<MovieResponseDto>> findAllMovies() {
        return new BaseResponse(HttpStatus.OK, "All Movies", movieService.findAllMovie());
    }
}
