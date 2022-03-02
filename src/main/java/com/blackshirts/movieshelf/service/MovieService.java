package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieRequestDto;
import com.blackshirts.movieshelf.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // @Autowired 어노테이션 없이 의존성 주입
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    private static MovieRepository movieRepository;

    public static void saveMovie(String movie_title, int movie_rank, String movie_poster, String movie_content_bold, String movie_content_detail) {
        log.info(movie_rank + "\t" + movie_title + "\t" + movie_poster + "\t" + movie_content_bold);
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setMovieTitle(movie_title);
        movieRequestDto.setMovieRank(movie_rank);
        movieRequestDto.setMoviePoster(movie_poster);
        movieRequestDto.setMovieContentBold(movie_content_bold);
        movieRequestDto.setMovieContentDetail(movie_content_detail);
        log.info(movieRequestDto.getMovieRank() + "\t" + movieRequestDto.getMovieTitle() + "\t" + movieRequestDto.getMoviePoster() + "\t" + movieRequestDto.getMovieContentBold());
        movieRepository.save(movieRequestDto.toEntity());
    }

//    @Transactional(readOnly = true)
//    public void saveMovie(MovieRequestDto movieRequestDto) {
//        movieRepository.save(movieRequestDto.toEntity());
//    }
}
