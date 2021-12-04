package com.evinfo.api.review.dto;

import com.evinfo.domain.review.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private final String content;
    private final Double star;

    public ReviewResponseDto(final Review review) {
        this.content = review.getContent();
        this.star = review.getStar();
    }
}
