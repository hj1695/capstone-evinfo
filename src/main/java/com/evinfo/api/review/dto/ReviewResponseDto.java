package com.evinfo.api.review.dto;

import com.evinfo.domain.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String content;
    private final Double star;
    private final LocalDateTime createdAt;

    public ReviewResponseDto(final Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.star = review.getStar();
        this.createdAt = review.getCreatedAt();
    }
}
