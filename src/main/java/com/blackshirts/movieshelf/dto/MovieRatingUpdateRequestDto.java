package com.blackshirts.movieshelf.dto;


import com.blackshirts.movieshelf.entity.Movie;
import com.blackshirts.movieshelf.entity.MovieRating;
import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieRatingUpdateRequestDto {
    private Long rateId;
    private User user;
    private Movie movie;
    private int movieRate;

    @Builder
    public MovieRatingUpdateRequestDto(User user, Movie movie, int movieRate) {
        this.user = user;
        this.movie = movie;
        this.movieRate = movieRate;
    }

    // dto -> entity
    public MovieRating toEntity() {
        return MovieRating.builder()
                .user(user)
                .movie(movie)
                .movieRate(movieRate)
                .build();
    }
}
