package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieRatingRequestDto;
import com.blackshirts.movieshelf.dto.MovieRatingResponseDto;
import com.blackshirts.movieshelf.entity.*;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.MovieRatingRepository;
import com.blackshirts.movieshelf.repository.MovieRepository;
import com.blackshirts.movieshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieRatingService {
    private final MovieRatingRepository movieRatingRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    //사용자가 이미 별점을 준 영화인지 확인
    private boolean isNotAlreadyRating(User user, Movie movie) {
        return movieRatingRepository.findByMovieAndUser(movie, user).isPresent();
    }

    @Transactional
    public Long create(MovieRatingRequestDto movieRatingDto) {
        User findUser = userRepository.findById(movieRatingDto.getUserId()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieRatingDto.getMovieId()).orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
        boolean isRateCheck = isNotAlreadyRating(findUser, movie);
        if (!isRateCheck) {
            try {
                movieRatingRepository.save(new MovieRating(movie, findUser, movieRatingDto.getMovieRate()));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MOVIE_RATE);
            }
        } else {
            throw new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE);
        }

        return movieRatingRepository.findByMovieAndUser(movie, findUser).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE)).getMovie().getMovieId();
    }

//    @Transactional
//    public Long update(MovieRatingRequestDto movieRatingDto) {
//
//        User findUser = userRepository.findById(movieRatingDto.getUserId()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
//        Movie movie = movieRepository.findById(movieRatingDto.getMovieId()).orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
//        boolean isRateCheck = isNotAlreadyRating(findUser, movie);
//        MovieRating movieRating = movieRatingRepository.findByMovieAndUser(movie, findUser).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE));
//
//        if (!isRateCheck) {
//            try {
//                movieRatingRepository.save(new MovieRating(movie, findUser, movieRatingDto.getMovieRate()));
//            } catch (Exception e) {
//                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MOVIE_RATE);
//            }
//        } else {
//            throw new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE);
//        }
//        return movieRatingRepository.findByMovieAndUser(movie, findUser).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE)).getLikeId();
//    }

}
