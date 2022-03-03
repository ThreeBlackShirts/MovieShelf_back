package com.blackshirts.movieshelf.util;

import com.blackshirts.movieshelf.service.MovieService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NaverMovieCrawling {

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
                            //MovieService.dtoSet(movie_title, movie_rank, movie_poster, movie_content_bold, movie_content_detail);
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
