package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String email);
    Optional<List<User>> findUserByUserKakaoIdentifier(String kakaoIdentifier);

    Optional<Boolean> existsByUserEmail(String email);

    Long deleteUserByUserEmail(String userEmail);

}
