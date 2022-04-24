package com.blackshirts.movieshelf.dto;

import com.blackshirts.movieshelf.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private String userEmail;
    private String title;
    private String content;

    @Builder
    public ReviewRequestDto(String userEmail, String title, String content) {
        this.userEmail = userEmail;
        this.title = title;
        this.content = content;
    }
}
