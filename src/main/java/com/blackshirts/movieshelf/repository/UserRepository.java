package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String email);
    // Optional<Boolean> existsByEmail(String email);

}
