package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByOrderByReviewIdDesc();
    List<Review> findAllByUser(User user);
}

