package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieRatingRequestDto;
import com.blackshirts.movieshelf.dto.MovieRatingResponseDto;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.User;
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

    @Transactional
    public Long create(MovieRatingRequestDto movieRatingDto) {

        return movieRatingRepository.save(movieRatingDto.toEntity()).getRateId();
    }

    @Transactional
    public Long update(MovieRatingRequestDto movieRatingDto) {

        return movieRatingRepository.save(movieRatingDto.toEntity()).getRateId();
    }

}
