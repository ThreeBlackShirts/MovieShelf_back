package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.entity.WishList;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.MovieRepository;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public Long createWish(User user, Long movieId) {

        User findUser = userRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
//        wishListRepository.findByUserAndMovie(findUser, movie).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_SAVE_LIKE));
        boolean isWishListCheck = isNotAlreadyWishList(findUser, movie);
        if (!isWishListCheck) {
            try {

                wishListRepository.save(new WishList(movie, findUser));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_LIKE);
            }
        } else {
            throw new BaseException(BaseResponseCode.DUPLICATE_SAVE_LIKE);
        }


        return wishListRepository.findByUserAndMovie(findUser, movie).orElseThrow(() -> new BaseException(BaseResponseCode.DUPLICATE_SAVE_WISH)).getWishListId();
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyWishList(User user, Movie movie) {
        return wishListRepository.findByUserAndMovie(user, movie).isPresent();
    }

    public Long delete(User user, Long movieId) {

        User findUser = userRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        boolean isWishListCheck = isNotAlreadyWishList(findUser, movie);
        Long wishId = null;
        if (isWishListCheck) {

            wishId = wishListRepository.findByUserAndMovie(findUser, movie).orElseThrow(() -> new BaseException(BaseResponseCode.WISH_NOT_FOUND)).getWishListId();
            wishListRepository.deleteById(wishId);
        }

        return wishId;
    }

}
