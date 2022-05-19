package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTrailerRepository extends JpaRepository<MovieTrailer, Long> {

}
