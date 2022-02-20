package com.blackshirts.movieshelf.jpa;

import com.blackshirts.movieshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
