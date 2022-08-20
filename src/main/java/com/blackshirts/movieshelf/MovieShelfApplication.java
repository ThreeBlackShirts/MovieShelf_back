package com.blackshirts.movieshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableSwagger2
@EnableWebMvc
@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = { "classpath:application-oauth.properties" })
public class MovieShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieShelfApplication.class, args);
    }

}
