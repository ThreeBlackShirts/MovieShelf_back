package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest //JPA 관련 테스트 설정만 로드
class MovieResponseDtoTest {

    @Test
    public void lombok_function_test(){
        //given
        int movieRank = 1;
        String movieTitle = "영화제목";
        String moviePoster = "포스터URL";
        Set<String> movieGenres = Collections.singleton("영화 장르");
        String movieContentBold = "한줄 줄거리";
        String movieContentDetail = "상세 줄거리";
        String movieContentDetailLong = "상세 줄거리2";

        //when
        MovieResponseDto dto = new MovieResponseDto(movieRank, movieTitle, moviePoster, movieGenres, movieContentBold, movieContentDetail, movieContentDetailLong);

        //then
        assertThat(dto.getMovieRank()).isEqualTo(movieRank); //assertThat: 테스트 검증 라이브러리
        assertThat(dto.getMovieTitle()).isEqualTo(movieTitle);
        assertThat(dto.getMoviePoster()).isEqualTo(moviePoster);
        assertThat(dto.getMoviePoster()).isEqualTo(movieGenres);
        assertThat(dto.getMovieContentBold()).isEqualTo(movieContentBold);
        assertThat(dto.getMovieContentDetail()).isEqualTo(movieContentDetail);
        assertThat(dto.getMovieContentDetailLong()).isEqualTo(movieContentDetailLong);
    }

}