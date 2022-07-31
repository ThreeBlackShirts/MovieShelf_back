package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.LikeResponseDto;
import com.blackshirts.movieshelf.dto.UserRequestDto;
import com.blackshirts.movieshelf.dto.WishListResponseDto;
import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.LikeRepository;
import com.blackshirts.movieshelf.repository.ReviewRepository;
import com.blackshirts.movieshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public LikeResponseDto findLike(UserRequestDto userRequestDto, Long reviewId){
        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Review review = getReviewByReviewId(reviewId);
        return new LikeResponseDto(findByUserAndReview(findUser, review));
    }

    public Long addLike(UserRequestDto userRequestDto, Long reviewId) {

        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Review review = getReviewByReviewId(reviewId);

        boolean isLikeCheck = isNotAlreadyLike(findUser, review);
        if (!isLikeCheck) {
            try {
                likeRepository.save(new Like(review, findUser));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_LIKE);
            }
        }else{
            throw new BaseException(BaseResponseCode.DUPLICATE_SAVE_LIKE);
        }

        return findByUserAndReview(findUser,review).getLikeId();
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(User user, Review review) {
        return likeRepository.findByUserAndReview(user, review).isPresent();
    }

    public Long delete(UserRequestDto userRequestDto, Long reviewId) {

        User findUser = getUserByUserEmail(userRequestDto.getUserEmail());
        Review review = getReviewByReviewId(reviewId);
        boolean isLikeCheck = isNotAlreadyLike(findUser, review);
        Long likeId = null;
        if (isLikeCheck) {
            likeId = findByUserAndReview(findUser, review).getLikeId();
            likeRepository.deleteById(likeId);
        }

        return likeId;
    }

    @Transactional(readOnly = true)
    public List<LikeResponseDto> searchByUser(String userEmail) {
        User user = getUserByUserEmail(userEmail);
        return likeRepository.findAllByUser(user).stream()
                .map(LikeResponseDto::new)
                .collect(Collectors.toList());
    }

    private Like findByUserAndReview(User user, Review review){
        return likeRepository.findByUserAndReview(user, review).orElseThrow(() -> new BaseException(BaseResponseCode.Like_NOT_FOUND));
    }

    private User getUserByUserEmail(String userEmail){
        return userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    private Review getReviewByReviewId(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

}
