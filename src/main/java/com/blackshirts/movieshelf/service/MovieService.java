package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieRequestDto;
import com.blackshirts.movieshelf.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor // @Autowired 어노테이션 없이 의존성 주입
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    public void saveMovie(MovieRequestDto movieRequestDto) {
        log.info(movieRequestDto.getMovieRank() + "\t" + movieRequestDto.getMovieTitle() + "\t" + movieRequestDto.getMoviePoster() + "\t" + movieRequestDto.getMovieContentBold());
        movieRepository.save(movieRequestDto.toEntity());
    }

    public void dtoSet(String movie_title, int movie_rank, String movie_poster, String movie_content_bold, String movie_content_detail){
        log.info(movie_rank + "\t" + movie_title + "\t" + movie_poster + "\t" + movie_content_bold);
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setMovieTitle(movie_title);
        movieRequestDto.setMovieRank(movie_rank);
        movieRequestDto.setMoviePoster(movie_poster);
        movieRequestDto.setMovieContentBold(movie_content_bold);
        movieRequestDto.setMovieContentDetail(movie_content_detail);

        saveMovie(movieRequestDto);
    }

//    @Transactional(readOnly = true)
//    public void saveMovie(MovieRequestDto movieRequestDto) {
//        movieRepository.save(movieRequestDto.toEntity());
//    }

    public void movieCrawling(){
        final String naver_movie_url = "https://movie.naver.com/movie/sdb/rank/rmovie.naver?sel=cnt&date=20220225";
        Connection conn = Jsoup.connect(naver_movie_url);

        try {
            Document document = conn.get();
            Elements movie_elements = document.getElementsByClass("tit3");

            int i = 1;
            for (Element elements : movie_elements.select("a")) {
                String movie_title = elements.text();
                int movie_rank = i;
                i++;

                String movie_detail_url = "https://movie.naver.com/" + elements.attr("href");

                Connection conn_detail = Jsoup.connect(movie_detail_url);

                try {
                    Document document_detail = conn_detail.get();
                    Elements movie_poster_elements = document_detail.getElementsByClass("poster");

                    int j = 0;
                    for (Element poster_elements : movie_poster_elements.select("img")) {
                        if(j != 0) {
                            String movie_poster = poster_elements.attr("src");

                            Elements movie_content_elements_bold = document_detail.getElementsByClass("h_tx_story");
                            String movie_content_bold = movie_content_elements_bold.text();

                            Elements movie_content_elements = document_detail.getElementsByClass("story_area");
                            String movie_content_detail = movie_content_elements.select("p").text(); //text() > toString() : <br>등이 포함된 내용 가져올 수 있음

                            //System.out.println(movie_rank + "\t" + movie_title + "\t" + movie_poster);
                            //System.out.println(movie_content_bold);  //영화 줄거리 첫 줄 굵은 글씨
                            //System.out.println(movie_content_detail); //영화 줄거리
                            dtoSet(movie_title, movie_rank, movie_poster, movie_content_bold, movie_content_detail);
                        }
                        else
                            j++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
