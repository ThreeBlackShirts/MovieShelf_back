package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.dto.MovieDetailResponseDto;
import com.blackshirts.movieshelf.dto.MovieResponseDto;
import com.blackshirts.movieshelf.dto.MovieSearchResponseDto;
import com.blackshirts.movieshelf.dto.MoviebannerResponseDto;
import com.blackshirts.movieshelf.exception.BaseResponse;
import com.blackshirts.movieshelf.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2. Movie"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    @ApiOperation(value = "크롤링 데이터 등록", notes = "크롤링 된 영화 정보를 등록합니다.")
    @GetMapping("/datainsert")
    public BaseResponse<String> movieSave() throws Exception {
        movieService.NaverMovieCrawling();
        return new BaseResponse(HttpStatus.OK, "크롤링 된 영화 정보가 등록되었습니다.",
                "크롤링 데이터");
    }

    @ApiOperation(value = "영화 목록 조회", notes = "등록된 모든 영화를 조회합니다.")
    @GetMapping("/alllist")
    public BaseResponse<List<MovieResponseDto>> findAllMovies() throws Exception {
        return new BaseResponse(HttpStatus.OK, "All Movies", movieService.findAllMovie());
    }

    @ApiOperation(value = "영화 검색", notes = "검색어가 포함된 영화제목과 포스터링크를 List 형태로 리턴합니다.\n검색 결과가 없을 경우 빈(\"\")리스트를 리턴합니다.")
    @PostMapping("/search")
    public BaseResponse<List<MovieSearchResponseDto>> searchMovie(@ApiParam(value = "검색창 입력내용", required = true) @RequestBody String input) throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return List(MovieTite and MoviePosterUrl)", movieService.searchMovie(input));
    }

    @ApiOperation(value = "영화 단건 추천", notes = "영화 하나를 랜덤으로 리턴합니다.")
    @PostMapping("/todaybanner")
    public BaseResponse<MoviebannerResponseDto> todayMovie() throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return Movie title, PosterUrl, detail", movieService.todayMovie());
    }

    @ApiOperation(value = "영화 추천 시스템", notes = "특정 영화의 장르가 비슷한 영화 목록을 리턴합니다.")
    @PostMapping("/recommendation")
    public BaseResponse<List<MovieSearchResponseDto>> recommendMovie() throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return Movie title, PosterUrl", movieService.recommendMovie());
    }

    @ApiOperation(value = "영화 국가별 리스트", notes = "특정 제조국이 같은 영화 목록을 리턴합니다.")
    @PostMapping("/nation")
    public BaseResponse<List<MovieSearchResponseDto>> nationMovie(@ApiParam(value = "제조국", required = true) @RequestBody String target) throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return Movie title, PosterUrl", movieService.nationMovie(target));
    }

    @ApiOperation(value = "영화 장르별 리스트", notes = "특정 장르가 비슷한 영화 목록을 리턴합니다.")
    @PostMapping("/genre")
    public BaseResponse<List<MovieSearchResponseDto>> genreMovie(@ApiParam(value = "장르", required = true) @RequestBody String target) throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return Movie title, PosterUrl", movieService.genreMovie(target));
    }

    @ApiOperation(value = "영화 컨텐츠 정보", notes = "영화 컨텐츠 정보를 리턴합니다.")
    @PostMapping("/detailed/{movieId}")
    public BaseResponse<MovieDetailResponseDto> detailedMovie(@ApiParam(value = "타겟 영화", required = true) @PathVariable Long movieId) throws Exception {
        return new BaseResponse(HttpStatus.OK, "Return Movie contents", movieService.detailedMovie(movieId));
    }

    @ApiOperation(value = "영화 목록 조회 top20", notes = "등록된 영화 중 top 20개를 조회합니다.")
    @GetMapping("/rank")
    public BaseResponse<List<MovieResponseDto>> findTop20() {
        return new BaseResponse(HttpStatus.OK, "Top 20 Movies", movieService.findTop20());
    }

}
