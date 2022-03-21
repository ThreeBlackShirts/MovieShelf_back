package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.MovieResponseDto;
import com.blackshirts.movieshelf.dto.MovieSearchResponseDto;
import com.blackshirts.movieshelf.dto.UserLoginRequestDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public BaseResponse<List<MovieResponseDto>> findAllMovies() throws Exception {
        return new BaseResponse(HttpStatus.OK, "All Movies", movieService.findAllMovie());
    }

    @ApiOperation(value = "영화 검색", notes = "검색어가 포함된 영화제목과 포스터링크를 리턴합니다.")
    @GetMapping("/searchmovie")
    public BaseResponse<List<MovieSearchResponseDto>> searchMovie(@ApiParam(value = "검색창 입력내용", required = true) @RequestBody String input) throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return MovieTite and MoviePosterUrl", movieService.serachMovie(input));
    }
}
