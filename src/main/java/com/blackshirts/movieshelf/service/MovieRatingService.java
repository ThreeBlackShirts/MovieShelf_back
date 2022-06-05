package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.MovieRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieRatingService {
    private final MovieRatingRepository movieRatingRepository;

    //사용자가 이미 별점을 준 영화인지 확인
    private boolean isNotAlreadyRating(User user, Movie movie) {
        return movieRatingRepository.findByMovieAndUser(movie, user).isPresent();
    }



}
