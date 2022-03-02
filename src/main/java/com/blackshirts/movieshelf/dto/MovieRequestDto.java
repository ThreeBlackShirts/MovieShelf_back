package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieRequestDto {
    private String movieTitle;
    private String moviePoster;
    private String movieContentBold;
    private String movieContentDetail;
    private int movieRank;


//    @Builder
//    public UserRequestDto(String userEmail, String userName) {
//        this.userEmail = userEmail;
//        this.userName = userName;
//    }

    @Builder
    public MovieRequestDto(String movieTitle, int movieRank, String moviePoster, String movieContentBold, String movieContentDetail) {
        this.movieTitle = movieTitle;
        this.movieRank = movieRank;
        this.moviePoster = moviePoster;
        this.movieContentBold = movieContentBold;
        this.movieContentDetail = movieContentDetail;
    }

    // dto -> entity
    public Movie toEntity() {
        return Movie.builder()
                .movieTitle(movieTitle)
                .movieRank(movieRank)
                .moviePoster(moviePoster)
                .movieContentBold(movieContentBold)
                .movieContentDetail(movieContentDetail)
                .build();
    }
}
