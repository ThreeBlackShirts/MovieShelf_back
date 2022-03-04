package com.blackshirts.movieshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableSwagger2
@EnableWebMvc
@SpringBootApplication
public class MovieShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieShelfApplication.class, args);
    }

}
