package com.blackshirts.movieshelf.repository;

import com.blackshirts.movieshelf.entity.MovieStillcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStillcutRepository  extends JpaRepository<MovieStillcut, Long> {

}
