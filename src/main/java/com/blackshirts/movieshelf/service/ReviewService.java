package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.ReviewCreateRequestDto;
import com.blackshirts.movieshelf.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Long create(ReviewCreateRequestDto requestDto) {
        return reviewRepository.save(requestDto.toEntity()).getReviewId();
    }
}
