package com.blackshirts.movieshelf;

import com.blackshirts.movieshelf.util.NaverMovieCrawling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
@EnableWebMvc
@EnableJpaAuditing
@SpringBootApplication
public class MovieShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieShelfApplication.class, args);
        NaverMovieCrawling.movieCrawling();
    }

}
