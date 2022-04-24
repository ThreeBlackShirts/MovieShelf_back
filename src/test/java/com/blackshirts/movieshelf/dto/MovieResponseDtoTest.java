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
        String movieGenres = "영화 장르";
        String movieNation = "제조 국가";
        String movieRunningTime = "상영 시간";
        String movieReleaseDate = "개봉일";
        String movieDirector = "감동";
        String movieActor = "출연진";
        String movieFilmrate = "연령등급";
        String movieContentBold = "한줄 줄거리";
        String movieContentDetail = "상세 줄거리";
        String movieContentDetailLong = "상세 줄거리2";

        //when
        MovieResponseDto dto = new MovieResponseDto(movieRank, movieTitle, moviePoster, movieGenres, movieNation, movieRunningTime, movieReleaseDate, movieDirector, movieActor, movieFilmrate, movieContentBold, movieContentDetail, movieContentDetailLong);

        //then
        assertThat(dto.getMovieRank()).isEqualTo(movieRank); //assertThat: 테스트 검증 라이브러리
        assertThat(dto.getMovieTitle()).isEqualTo(movieTitle);
        assertThat(dto.getMoviePoster()).isEqualTo(moviePoster);
        assertThat(dto.getMovieGenres()).isEqualTo(movieGenres);
        assertThat(dto.getMovieNation()).isEqualTo(movieNation);
        assertThat(dto.getMovieRunningTime()).isEqualTo(movieRunningTime);
        assertThat(dto.getMovieReleaseDate()).isEqualTo(movieReleaseDate);
        assertThat(dto.getMovieReleaseDate()).isEqualTo(movieDirector);
        assertThat(dto.getMovieReleaseDate()).isEqualTo(movieActor);
        assertThat(dto.getMovieReleaseDate()).isEqualTo(movieFilmrate);
        assertThat(dto.getMovieContentBold()).isEqualTo(movieContentBold);
        assertThat(dto.getMovieContentDetail()).isEqualTo(movieContentDetail);
        assertThat(dto.getMovieContentDetailLong()).isEqualTo(movieContentDetailLong);
    }

}