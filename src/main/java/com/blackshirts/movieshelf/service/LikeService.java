package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.LikeRepository;
import com.blackshirts.movieshelf.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    public Long addLike(User user, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        likeRepository.findByUserAndReview(user, review).orElseThrow(() -> new BaseException(BaseResponseCode.Like_NOT_FOUND));
        boolean isLikeCheck = isNotAlreadyLike(user, review);
        if(!isLikeCheck) {
            try {
                likeRepository.save(new Like(review, user));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_LIKE);
            }
        }
        return  likeRepository.findByUserAndReview(user, review).orElseThrow(() -> new BaseException(BaseResponseCode.Like_NOT_FOUND)).getLikeId();
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(User user, Review review) {
        return likeRepository.findByUserAndReview(user, review).isPresent();
    }

    public Long delete(User user, Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        boolean isLikeCheck = isNotAlreadyLike(user, review);

        Long likeId = likeRepository.findByUserAndReview(user, review).get().getLikeId();
        likeRepository.deleteById(likeId);
        return likeId;
    }
}
