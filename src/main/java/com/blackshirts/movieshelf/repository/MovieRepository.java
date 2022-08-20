package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //기본기능(sava(), findOne(), findAll(), count(), delete())을 제외한
    //조회 기능을 추가하고 싶으면 규칙에 맞는 메서드를 추가
    // EX) Movie findBytitle(String search_word);

    Optional<Movie> findByMovieTitle(String title);
    Movie findByMoviePoster(String poster);
    List<Movie> findByMovieTitleContaining(String input);
    boolean existsByMovieTitle(String title);
    List<Movie> findByMovieNationContaining(String target);
    List<Movie> findByMovieGenresContaining(String target);

    List<Movie> findTop20ByOrderByMovieRateDesc();

    List<Movie> findByMovieNationContainingOrderByMovieRate(String target);

    @Query(value = "SELECT movie_title FROM (SELECT movie_title FROM movies ORDER BY dbms_random.value) WHERE rownum = 1", nativeQuery = true)
    String getRandomMoviesTitle();

    @Query(value = "SELECT * FROM (SELECT * FROM movies ORDER BY dbms_random.value) WHERE rownum = 1", nativeQuery = true)
    Movie getRandomMovies();
}
