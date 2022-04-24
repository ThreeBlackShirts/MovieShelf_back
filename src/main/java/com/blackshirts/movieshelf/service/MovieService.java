package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieRequestDto;
import com.blackshirts.movieshelf.dto.MovieResponseDto;
import com.blackshirts.movieshelf.dto.MovieSearchResponseDto;
import com.blackshirts.movieshelf.entity.Movie;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // @Autowired 어노테이션 없이 의존성 주입
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public void saveMovie(MovieRequestDto movieRequestDto) {
        log.info(movieRequestDto.getMovieRank() + "\t" + movieRequestDto.getMovieTitle() + "\t" + movieRequestDto.getMoviePoster());
        //log.info(movieRequestDto.getMovieContentBold() + "\n" + movieRequestDto.getMovieContentDetail() + movieRequestDto.getMovieContentDetailLong());
        //log.info(movieRequestDto.getMovieGenres() + "\t" + movieRequestDto.getMovieNation() + "\t" + movieRequestDto.getMovieRunningTime() + "\t" + movieRequestDto.getMovieReleaseDate());
        movieRepository.save(movieRequestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public void dtoSet(String movie_title, int movie_rank, String movie_poster, String movie_genres, String movie_nation, String running_time, String release_date, String director, String filmrate, String actor, String movie_content_bold, String movie_content_detail){
        //log.info(movie_rank + "\t" + movie_title + "\t" + movie_poster + "\t" + movie_content_bold);
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setMovieTitle(movie_title);
        movieRequestDto.setMovieRank(movie_rank);
        movieRequestDto.setMoviePoster(movie_poster);
        movieRequestDto.setMovieGenres(movie_genres);
        movieRequestDto.setMovieNation(movie_nation);
        movieRequestDto.setMovieDirector(director);
        movieRequestDto.setMovieFilmrate(filmrate);
        movieRequestDto.setMovieActor(actor);
        movieRequestDto.setMovieRunningTime(running_time);
        movieRequestDto.setMovieReleaseDate(release_date);
        movieRequestDto.setMovieContentBold(movie_content_bold);

        if(movie_content_detail.length() > 255) {
            String movie_content_details = movie_content_detail.substring(0, 255);
            String movie_content_detail_Long = movie_content_detail.substring(255, movie_content_detail.length());

            movieRequestDto.setMovieContentDetail(movie_content_details);
            movieRequestDto.setMovieContentDetailLong(movie_content_detail_Long);
        }
        else {
            movieRequestDto.setMovieContentDetail(movie_content_detail);
        }
        saveMovie(movieRequestDto);
    }

    @Transactional(readOnly = true)
    public void NavermovieCrawling(){
        final String naver_movie_url = "https://movie.naver.com/movie/sdb/rank/rmovie.naver";
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

                            Elements movie_genre = document_detail.select("dl.info_spec dd p span");
                            String movie_genres = "";
                            Elements info_spec = movie_genre.tagName("span");
                            Element genres_elements = info_spec.get(0);
                            for (Element genre : genres_elements.select("a")) {
                                if(genre != genres_elements.select("a").last())
                                    movie_genres += genre.text() + ", ";
                                else
                                    movie_genres += genre.text();
                            }

                            //제조국
                            String movie_nation = "";
                            if(info_spec.get(1).childNodeSize() != 1) {
                                Element nation_elements = info_spec.get(1);
                                for (Element nation : nation_elements.select("a")) {
                                    if (nation != nation_elements.select("a").last())
                                        movie_nation += nation.text() + ", ";
                                    else
                                        movie_nation += nation.text();
                                }
                            }
                            else {
                                movie_nation = info_spec.text();
                            }

                            //상영시간, 개봉일
                            String running_time = "";
                            String release_date = "";
                            if(info_spec.size() != 2) {
                                if(info_spec.get(2).select("a").size() == 0) {
                                    running_time = info_spec.get(2).ownText().trim();
                                    if(info_spec.size() != 3) {
                                        release_date =  info_spec.get(3).select("a").text().replace(" ", "");
                                    }
                                }
                                else {
                                    release_date = info_spec.get(2).select("a").text().replace(" ", "");
                                }
                            }

                            //감독, 출연, 연령등급
                            String director = "";
                            String actor = "";
                            String filmrate = "";
                            Elements movie_infos = document_detail.select("dl.info_spec dd");
                            Elements movie_infos_dt = document_detail.select("dl.info_spec dt");
                            for(int l = 1; l < movie_infos.size(); l++){
                                if(movie_infos_dt.get(l).attr("class").equals("step2")){
                                    director = movie_infos.get(l).select("p a").get(0).text();
                                }
                                if(movie_infos_dt.get(l).attr("class").equals("step4")){
                                    filmrate = movie_infos.get(l).select("p a").get(0).text();
                                }
                                if(movie_infos_dt.get(l).attr("class").equals("step3")){
                                    String movie_actor_url = "https://movie.naver.com/movie/bi/mi/" + movie_infos.get(l).select("a.more").attr("href");

                                    Connection conn_actor = Jsoup.connect(movie_actor_url);

                                    try {
                                        Document document_actor = conn_actor.get();
                                        Elements movie_actor_elements = document_actor.select("div.p_info a.k_name");
                                        int limit = 0;
                                        for(Element element : movie_actor_elements){
                                            limit++;
                                            if(element != movie_actor_elements.last() && limit != 15)
                                                actor += element.text() + ", ";
                                            else {
                                                actor += element.text();
                                                break;
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            //영화 줄거리(굵은 글씨, 얇은 글씨)
                            Elements movie_content_elements_bold = document_detail.getElementsByClass("h_tx_story");
                            String movie_content_bold = movie_content_elements_bold.text();

                            Elements movie_content_elements = document_detail.getElementsByClass("story_area");
                            String movie_content_detail = movie_content_elements.select("p").text(); //text() > toString() : <br>등이 포함된 내용 가져올 수 있음

//                            //영화 스틸컷
//                            List<String> stillcutList = new ArrayList<>();
//                            String movie_stillcut_url = "https://movie.naver.com/movie/bi/mi/" + document_detail.select("ul.end_sub_tap li a.tab03").attr("href");
//                            Connection conn_stillcut = Jsoup.connect(movie_stillcut_url);
//                            try {
//                                Document document_actor = conn_stillcut.get();
//                                Elements movie_stillcut_elements = document_actor.select("div.rolling_list ul li");
//                                Elements movie_stillcut_imgs = document_actor.select("div.img_obj div.img_ar");
//                                int limit2 = 0;
//                                for(Element element : movie_stillcut_elements){
//                                    limit2++;
//                                    if(element != movie_stillcut_elements.last() && limit2 <= 8) {
//                                        stillcutList.add(movie_stillcut_imgs.select("div.viewer_img img").attr("src"));
//                                        movie_stillcut_imgs.select("a.pic_next").
//                                    }
//                                    else {
//                                        actor += element.text();
//                                        break;
//                                    }
//                                }
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            //영화 트레일러(예고편)
//                            String movie_trailer_url = "https://movie.naver.com/movie/bi/mi/" + document_detail.select("ul.end_sub_tap li a.tab04").attr("href");
//
//                            Connection conn_trailer = Jsoup.connect(movie_trailer_url);
//
//                            try {
//                                Document document_actor = conn_trailer.get();
//                                Elements movie_trailer_elements = document_actor.select("div.p_info a.k_name");
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }

                            dtoSet(movie_title,
                                    movie_rank,
                                    movie_poster,
                                    movie_genres,
                                    movie_nation,
                                    running_time,
                                    release_date,
                                    director,
                                    filmrate,
                                    actor,
                                    movie_content_bold,
                                    movie_content_detail);
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

    @Transactional(readOnly = true)
    public List<MovieResponseDto> findAllMovie() {
        return movieRepository.findAll()
                .stream()
                .map(MovieResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovieSearchResponseDto> serachMovie(String input) {
        List<Movie> movies = movieRepository.findByMovieTitleContaining(input);
        List<MovieSearchResponseDto> movie_list = new ArrayList<>();
        if (movies.isEmpty() || movies == null){
            return movie_list;
        }
        else{
//            for (Movie dto : movies) {
//                log.info(dto.getMovieTitle() + "\t" + dto.getMoviePoster()); repository contains 확인
//            }
            return movies.stream().map(MovieSearchResponseDto::new).collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public List<MovieSearchResponseDto> recommendMovie(String target) {
        URL url = null;
        String encodeData = "";
        String decodeData = "";
        try {
            encodeData = URLEncoder.encode(target, "UTF-8");
            target = encodeData.replace("+", "%20");
            url = new URL("http://127.0.0.1:5000/movie/recommend/" + target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");
            connection.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeUTF(target);
            outputStream.flush();
            outputStream.close();

            try{
                StringBuffer sb = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                while(br.ready()) {
                    sb.append(br.readLine());
                }
                System.out.println(sb);
            }catch(Exception e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<MovieSearchResponseDto> movie_list = new ArrayList<>();
        return movie_list;
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDto> detailedMovie(String target) {
        List<Movie> movies = movieRepository.findByMovieTitleContaining(target);
        List<MovieResponseDto> movie_list = new ArrayList<>();
        if (movies.isEmpty() || movies == null){
            return movie_list;
        }
        else{
//            for (Movie dto : movies) {
//                log.info(dto.getMovieTitle() + "\t" + dto.getMoviePoster()); repository contains 확인
//            }
            return movies.stream().map(MovieResponseDto::new).collect(Collectors.toList());
        }
    }
}
