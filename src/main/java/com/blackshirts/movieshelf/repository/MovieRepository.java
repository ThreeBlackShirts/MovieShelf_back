package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Movie;
import org.jsoup.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    //기본기능(sava(), findOne(), findAll(), count(), delete())을 제외한
    //조회 기능을 추가하고 싶으면 규칙에 맞는 메서드를 추가
    // EX) Movie findBytitle(String search_word);
}
