package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.repository.MovieRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieRatingService {
    private final MovieRatingRepository movieRatingRepository;



}
