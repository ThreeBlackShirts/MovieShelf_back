package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.MovieDetailResponseDto;
import com.blackshirts.movieshelf.dto.MovieRequestDto;
import com.blackshirts.movieshelf.dto.MovieResponseDto;
import com.blackshirts.movieshelf.dto.MovieSearchResponseDto;
import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieStillcut;
import com.blackshirts.movieshelf.entity.MovieTrailer;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.MovieRepository;
import com.blackshirts.movieshelf.repository.MovieStillcutRepository;
import com.blackshirts.movieshelf.repository.MovieTrailerRepository;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // @Autowired 어노테이션 없이 의존성 주입
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final MovieStillcutRepository movieStillcutRepository;
    private final MovieTrailerRepository movieTrailerRepository;

    @Transactional(readOnly = true)
    public void saveMovie(MovieRequestDto movieRequestDto, List<String> stillcutList, List<String> trailerList) {
        log.info(movieRequestDto.getMovieRank() + "\t" + movieRequestDto.getMovieTitle() + "\t" + movieRequestDto.getMoviePoster());
        //log.info(movieRequestDto.getMovieContentBold() + "\n" + movieRequestDto.getMovieContentDetail() + movieRequestDto.getMovieContentDetailLong());
        //log.info(movieRequestDto.getMovieGenres() + "\t" + movieRequestDto.getMovieNation() + "\t" + movieRequestDto.getMovieRunningTime() + "\t" + movieRequestDto.getMovieReleaseDate());
        movieRepository.save(movieRequestDto.toEntity());

        Movie movie = movieRepository.findByMovieTitle(movieRequestDto.getMovieTitle()).orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
        for(String stillcut : stillcutList){
            //movieStillcutRepository.findByMovie(movie).orElseThrow(() -> new BaseException(BaseResponseCode.MOVIE_NOT_FOUND));

            try {
                movieStillcutRepository.save(new MovieStillcut(movie, stillcut));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_STILLCUT);
            }
        }

        for(String trailer : trailerList){
            //movieTrailerRepository.findByMovie(movie).orElseThrow(() -> new BaseException(BaseResponseCode.MOVIE_NOT_FOUND));

            try {
                movieTrailerRepository.save(new MovieTrailer(movie, trailer));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_STILLCUT);
            }
        }
    }

    @Transactional(readOnly = true)
    public void dtoSet(String movie_title, String movie_poster, String movie_genres, String movie_nation, String running_time, String release_date,
                       String director, String filmrate, String actor, String movie_content_bold, String movie_content_detail,
                       List<String> stillcutList, List<String> trailerList){
        //log.info(movie_rank + "\t" + movie_title + "\t" + movie_poster + "\t" + movie_content_bold);
        MovieRequestDto movieRequestDto = MovieRequestDto.builder()
                .movieTitle(movie_title)
                .moviePoster(movie_poster)
                .movieGenres(movie_genres)
                .movieNation(movie_nation)
                .movieDirector(director)
                .movieFilmrate(filmrate)
                .movieActor(actor)
                .movieRunningTime(running_time)
                .movieReleaseDate(release_date)
                .movieContentBold(movie_content_bold)
                .movieContentDetail(movie_content_detail)
                .build();

        saveMovie(movieRequestDto, stillcutList, trailerList);
    }

    @Transactional(readOnly = true)
    public void NaverMovieCrawling(){
        final String naver_movie_url = "https://movie.naver.com/movie/sdb/rank/rmovie.naver";
        Connection conn = Jsoup.connect(naver_movie_url);

        try {
            Document document = conn.get();
            Elements movie_elements = document.getElementsByClass("tit3");

            //int i = 1;
            for (Element elements : movie_elements.select("a")) {
                String movie_title = elements.text();
                if(movieRepository.existsByMovieTitle(movie_title)){
                    System.out.println("이미 등록된 영화입니다.");
                    continue;
                }
                //int movie_rank = i;
                //i++;

                String movie_detail_url = "https://movie.naver.com/" + elements.attr("href");

                Connection conn_detail = Jsoup.connect(movie_detail_url);

                try {
                    Document document_detail = conn_detail.get();
                    Elements movie_poster_elements = document_detail.getElementsByClass("poster");

                    int j = 0;
                    for (Element poster_elements : movie_poster_elements.select("img")) {
                        if(j != 0) {
                            String movie_poster = poster_elements.attr("src");

                            //장르, 제조국, 상영시간, 개봉일, 감독, 출연, 연령등급
                            String movie_genres = "";
                            String movie_nation = "";
                            String running_time = "";
                            String release_date = "";
                            String director = "";
                            String actor = "";
                            String filmrate = "";
                            Elements movie_infos = document_detail.select("dl.info_spec dd");
                            Elements movie_infos_dt = document_detail.select("dl.info_spec dt");
                            for(int l = 0; l < movie_infos.size(); l++){
                                if(movie_infos_dt.get(l).attr("class").equals("step1")){
                                    Elements movie_info = movie_infos.select("p span");

                                    //장르
                                    Element genres_elements = movie_info.get(0);
                                    for (Element genre : genres_elements.select("a")) {
                                        if(genre != genres_elements.select("a").last())
                                            movie_genres += genre.text() + ", ";
                                        else
                                            movie_genres += genre.text();
                                    }

                                    //제조국
                                    if(movie_info.get(1).childNodeSize() != 1) {
                                        Element nation_elements = movie_info.get(1);
                                        for (Element nation : nation_elements.select("a")) {
                                            if (nation != nation_elements.select("a").last())
                                                movie_nation += nation.text() + ", ";
                                            else
                                                movie_nation += nation.text();
                                        }
                                    }
                                    else {
                                        movie_nation = movie_info.text();
                                    }

                                    //상영시간, 개봉일
                                    if(movie_info.size() != 2) {
                                        if(movie_info.get(2).select("a").size() == 0) {
                                            running_time = movie_info.get(2).ownText().trim();
                                            if(movie_info.size() != 3) {
                                                release_date =  movie_info.get(3).select("a").text().replace(" ", "");
                                            }
                                        }
                                        else {
                                            release_date = movie_info.get(2).select("a").text().replace(" ", "");
                                        }
                                    }
                                }
                                else if(movie_infos_dt.get(l).attr("class").equals("step2")){
                                    director = movie_infos.get(l).select("p a").get(0).text();
                                }
                                else if(movie_infos_dt.get(l).attr("class").equals("step4")){
                                    filmrate = movie_infos.get(l).select("p a").get(0).text();
                                }
                                else if(movie_infos_dt.get(l).attr("class").equals("step3")){
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

                            //영화 스틸컷
                            List<String> stillcutList = new ArrayList<>();
                            List<String> stillcutUrlList = new ArrayList<>();
                            if(document_detail.select("div.sub_tab_area ul.end_sub_tab li a.tab04").attr("href") != "") {
                                Connection conn_photo = Jsoup.connect("https://movie.naver.com/movie/bi/mi/" + document_detail.select("div.sub_tab_area ul.end_sub_tab li a.tab03").attr("href"));
                                try {
                                    Document document_photo = conn_photo.get();
                                    String movie_stillcut_url = "https://movie.naver.com/movie/bi/mi/" + document_photo.select("div.btn_view_mode a.cick_off").attr("href");
                                    Connection conn_stillcut_list = Jsoup.connect(movie_stillcut_url);
                                    try {
                                        Document document_stillcut_list = conn_stillcut_list.get();
                                        Elements movie_stillcut_elements = document_stillcut_list.select("div.gallery_group ul li._brick a");
                                        int lim_count = 0;
                                        for (Element element : movie_stillcut_elements) {
                                            lim_count++;
                                            stillcutUrlList.add("https://movie.naver.com/movie/bi/mi/" + element.attr("href"));
                                            if (lim_count >= 8)
                                                break;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    for (String stillcutUrl : stillcutUrlList) {
                                        Connection conn_stillcut = Jsoup.connect(stillcutUrl);
                                        try {
                                            Document document_stillcut = conn_stillcut.get();
                                            stillcutList.add(document_stillcut.select("div.img_ar div.viewer_img img").attr("src"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            //영화 트레일러(예고편)
                            List<String> trailerList = new ArrayList<>();
                            List<String> trailertitleList = new ArrayList<>();
                            List<String> trailerUrlList = new ArrayList<>();
                            if(document_detail.select("div.sub_tab_area ul.end_sub_tab li a.tab04").attr("href") != "") {
                                Connection conn_video = Jsoup.connect("https://movie.naver.com/movie/bi/mi/" + document_detail.select("div.sub_tab_area ul.end_sub_tab li a.tab04").attr("href"));
                                try {
                                    Document document_trailer_list = conn_video.get();
                                    Elements movie_trailer_elements = document_trailer_list.select("div.ifr_area ul.video_thumb li a.video_obj");
                                    int lim_count = 0;
                                    for (Element element : movie_trailer_elements) {
                                        lim_count++;
                                        trailertitleList.add(element.attr("title"));
                                        trailerUrlList.add("https://movie.naver.com" + element.attr("href"));
                                        if (lim_count >= 8)
                                            break;
                                    }
                                    for (String trailerUrlUrl : trailerUrlList) {
                                        Connection conn_trailer = Jsoup.connect(trailerUrlUrl);
                                        try {
                                            Document document_trailer = conn_trailer.get();
                                            trailerList.add("https://movie.naver.com" + document_trailer.select("div.video_area div.video_ar iframe").attr("src"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            dtoSet(movie_title,
                                    //movie_rank,
                                    movie_poster,
                                    movie_genres,
                                    movie_nation,
                                    running_time,
                                    release_date,
                                    director,
                                    filmrate,
                                    actor,
                                    movie_content_bold,
                                    movie_content_detail,
                                    stillcutList,
                                    trailerList);
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
    public List<MovieSearchResponseDto> searchMovie(String input) {
        input = input.replace("\"", "");
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
    public List<MovieSearchResponseDto> recommendMovie() {
        List<Movie> recommendMovies = new ArrayList<>();
        List<MovieSearchResponseDto> movie_list = new ArrayList<>();
        String target = movieRepository.getRandomMovies();
        System.out.println(target);
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
                String recommendMovie = sb.toString();
                recommendMovie = recommendMovie.replace("[", "");
                recommendMovie = recommendMovie.replace("]", "");
                recommendMovie = recommendMovie.replace("{", "");
                recommendMovie = recommendMovie.replace("}", "");
                recommendMovie = recommendMovie.replace("\\", "");
                recommendMovie = recommendMovie.replace("\"", "");
                recommendMovie = recommendMovie.replace("movie_poster:", "");
                String[] data = recommendMovie.split(",");

                for(String poster : data) {
                    recommendMovies.add(movieRepository.findByMoviePoster(poster));
                }

            }catch(Exception e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (recommendMovies.isEmpty() || recommendMovies == null){
            return movie_list;
        }
        else{
            return recommendMovies.stream().map(MovieSearchResponseDto::new).collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public MovieDetailResponseDto detailedMovie(String target) {
        Movie movie = movieRepository.findByMovieTitle(target).orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));

        List<MovieStillcut> stillcuts = movieStillcutRepository.findByMovie(movie);
        List<String> stillcutList = new ArrayList<>();
        for(MovieStillcut movieStillcut : stillcuts){
            stillcutList.add(movieStillcut.getStillcut());
        }
        List<MovieTrailer> trailers = movieTrailerRepository.findByMovie(movie);
        List<String> trailerList = new ArrayList<>();
        for(MovieTrailer movieTrailer : trailers){
            trailerList.add(movieTrailer.getTralier());
        }

        MovieDetailResponseDto movieDetailResponseDto = new MovieDetailResponseDto();
        movieDetailResponseDto.setMovieTitle(movie.getMovieTitle());
        movieDetailResponseDto.setMoviePoster(movie.getMoviePoster());
        movieDetailResponseDto.setMovieGenres(movie.getMovieGenres());
        movieDetailResponseDto.setMovieNation(movie.getMovieNation());
        movieDetailResponseDto.setMovieRunningTime(movie.getMovieRunningTime());
        movieDetailResponseDto.setMovieReleaseDate(movie.getMovieReleaseDate());
        movieDetailResponseDto.setMovieDirector(movie.getMovieDirector());
        movieDetailResponseDto.setMovieActor(movie.getMovieActor());
        movieDetailResponseDto.setMovieFilmrate(movie.getMovieFilmrate());
        movieDetailResponseDto.setMovieContentBold(movie.getMovieContentBold());
        movieDetailResponseDto.setMovieContentDetail(movie.getMovieContentDetail());
        movieDetailResponseDto.setMovieStillcut(stillcutList);
        movieDetailResponseDto.setMovieTrailer(trailerList);

//            for (Movie dto : movies) {
//                log.info(dto.getMovieTitle() + "\t" + dto.getMoviePoster()); repository contains 확인
//            }
        return movieDetailResponseDto;
    }
}
