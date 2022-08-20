package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieRatingRequestDto;
import com.blackshirts.movieshelf.entity.*;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.MovieRatingRepository;
import com.blackshirts.movieshelf.repository.MovieRepository;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.util.MovieRankUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieRatingService {
    private final MovieRatingRepository movieRatingRepository;

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    //사용자가 이미 별점을 준 영화인지 확인
    private boolean isNotAlreadyMovieRating(Long movieId, Long userId) {
        return movieRatingRepository.findMovieRatingByMovieIdAndAndUserId(movieId, userId).isPresent();
    }

    public Long create(MovieRatingRequestDto movieRatingDto) {
        Long movieId = movieRatingDto.getMovieId();
        String userEmail = movieRatingDto.getUserEmail();

        User user = getUserByUserEmail(userEmail);
        Long userId = user.getUserId();

        boolean isRateCheck = isNotAlreadyMovieRating(movieId, userId);
        if (!isRateCheck) {
            try {
                movieRatingRepository.save(new MovieRating(movieId, userId, movieRatingDto.getMovieRate()));

                List<Integer> findMovieRateList = findMovieRatingByMovieId(movieId);
                double newMovieRate = MovieRankUtil.calculateRank(findMovieRateList);
                setNewMovieRate(newMovieRate, movieId);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MOVIE_RATE);
            }
        } else {
            throw new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE);
        }

        return movieRatingRepository.findMovieRatingByMovieIdAndAndUserId(movieId, userId).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_SAVE_MOVIE_RATE)).getMovieId();
    }

    public Long updateMovieRate(MovieRatingRequestDto movieRatingDto) {

        return update(movieRatingDto).orElseThrow(() -> new BaseException(BaseResponseCode.MOVIE_RATE_NOT_FOUND)).getRateId();
    }

    private Optional<MovieRating> update(MovieRatingRequestDto movieRatingDto) throws BaseException {
        Long movieId = movieRatingDto.getMovieId();
        String userEmail = movieRatingDto.getUserEmail();
        User user = getUserByUserEmail(userEmail);
        Long userId = user.getUserId();
        Movie movie = getMovieByMovieId(movieId);
        Optional<MovieRating> movieRating = movieRatingRepository.findMovieRatingByMovieIdAndAndUserId(movieId, userId);
        try {
            movieRating.ifPresent(selectMovieRate -> {
                selectMovieRate.setMovieRate(movieRatingDto.getMovieRate());
                movieRatingRepository.save(selectMovieRate);
            });
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return movieRating;
    }

    private User getUserByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    private Movie getMovieByMovieId(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new BaseException(BaseResponseCode.MOVIE_NOT_FOUND));
    }

    private List<Integer> findMovieRatingByMovieId(Long movieId) {

        List<MovieRating> movieRatingList = movieRatingRepository.findMovieRatingByMovieId(movieId);
        List<Integer> findMovieRateList = new ArrayList<>();

        for (MovieRating movieRating : movieRatingList) {
            findMovieRateList.add(movieRating.getMovieRate());
        }

        return findMovieRateList;
    }

    private double setNewMovieRate(double movieRate, Long movieId) {

        Optional<Movie> movie = movieRepository.findById(movieId);
        try {
            movie.ifPresent(selectMovieRate -> {
                selectMovieRate.setMovieRate(movieRate);
                movieRepository.save(selectMovieRate);
            });
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return movieRate;
    }

}
