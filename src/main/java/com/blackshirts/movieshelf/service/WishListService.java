package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.LikeResponseDto;
import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.dto.WishListResponseDto;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.entity.WishList;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.MovieRepository;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public WishListResponseDto findWishList(UserRequestDto userRequestDto, Long movieId) {
        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Movie movie = getMovieByMovieId(movieId);
        return new WishListResponseDto(findByUserAndMovie(findUser, movie));
    }

    public Long createWish(UserRequestDto userRequestDto, Long movieId) {

        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Movie movie = getMovieByMovieId(movieId);

        boolean isWishListCheck = isNotAlreadyWishList(findUser, movie);
        if (!isWishListCheck) {
            try {

                wishListRepository.save(new WishList(movie, findUser));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_WISH);
            }
        } else {
            throw new BaseException(BaseResponseCode.DUPLICATE_SAVE_WISH);
        }


        return findByUserAndMovie(findUser, movie).getWishListId();
    }

    public boolean validateWishList(UserRequestDto userRequestDto, Long movieId) {
        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Movie movie = getMovieByMovieId(movieId);
        return isNotAlreadyWishList(findUser, movie);
    }

    //사용자가 이미 위시리스트에 담았는지 존재 여부 확인
    private boolean isNotAlreadyWishList(User user, Movie movie) {
        return wishListRepository.findByUserAndMovie(user, movie).isPresent();
    }

    public Long delete(UserRequestDto userRequestDto, Long movieId) {

        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Movie movie = getMovieByMovieId(movieId);

        boolean isWishListCheck = isNotAlreadyWishList(findUser, movie);
        Long wishId = null;
        if (isWishListCheck) {
            wishId = findByUserAndMovie(findUser, movie).getWishListId();
            wishListRepository.deleteById(wishId);
        }

        return wishId;
    }

    @Transactional(readOnly = true)
    public List<WishListResponseDto> searchByUser(String userEmail) {
        User user = getUserByUserEmail(userEmail);
        return wishListRepository.findAllByUser(user).stream()
                .map(WishListResponseDto::new)
                .collect(Collectors.toList());
    }

    private WishList findByUserAndMovie(User user, Movie movie) {
        return wishListRepository.findByUserAndMovie(user, movie).orElseThrow(() -> new BaseException(BaseResponseCode.WISH_NOT_FOUND));
    }

    private User getUserByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    private Movie getMovieByMovieId(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("해당 위시리스트가 존재하지 않습니다."));
    }

}
