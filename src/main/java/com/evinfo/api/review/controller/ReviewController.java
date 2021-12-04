package com.evinfo.api.review.controller;

import com.evinfo.api.review.dto.ReviewCreateRequestDto;
import com.evinfo.api.review.dto.ReviewResponseDto;
import com.evinfo.api.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody @Valid final ReviewCreateRequestDto request) {
        final ReviewResponseDto response = reviewService.createReview(request);
        final URI uri = URI.create("/api/reviews");

        return ResponseEntity.created(uri)
                .body(response);
    }
}
