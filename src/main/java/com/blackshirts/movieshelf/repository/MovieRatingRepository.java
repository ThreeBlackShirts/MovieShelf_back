package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.MovieRating;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    Optional<MovieRating> findMovieRatingByMovieIdAndAndUserId(Long movieId, Long userId);
    List<MovieRating> findMovieRatingByMovieId(Long movieId);
}
