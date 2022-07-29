package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.MovieRating;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    Optional<MovieRating> findByMovieAndUser(Movie movie, User user);
}
