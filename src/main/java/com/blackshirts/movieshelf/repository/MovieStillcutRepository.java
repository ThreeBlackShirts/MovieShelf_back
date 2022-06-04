package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieStillcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieStillcutRepository extends JpaRepository<MovieStillcut, Long> {
    List<MovieStillcut> findByMovie(Movie movie);
}
