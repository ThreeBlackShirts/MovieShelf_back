package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.*;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long create(ReviewCreateRequestDto requestDto) {
        return reviewRepository.save(requestDto.toEntity()).getReviewId();
    }

    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        review.update(requestDto.getTitle(), requestDto.getContent());

        reviewRepository.save(review);

        return id;
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto searchById(Long review_id) {
        Review review = reviewRepository.findById(review_id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        return new ReviewResponseDto(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> searchByUser(User user) {
        return reviewRepository.findAllByUser(user).stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> searchByMovie(Movie movie) {
        return reviewRepository.findAllByMovie(movie).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> searchAllDesc() {
        return reviewRepository.findAllByOrderByReviewIdDesc().stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        reviewRepository.delete(review);
    }
}
