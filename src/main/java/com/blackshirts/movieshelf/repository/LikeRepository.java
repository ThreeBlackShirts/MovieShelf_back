package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.Like;
import com.blackshirts.movieshelf.entity.Review;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndReview(User user, Review review);

    List<Like> findAllByUser(User user);
}