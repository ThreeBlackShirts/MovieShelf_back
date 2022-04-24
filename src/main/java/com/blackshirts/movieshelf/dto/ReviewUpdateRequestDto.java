package com.blackshirts.movieshelf.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public ReviewUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
