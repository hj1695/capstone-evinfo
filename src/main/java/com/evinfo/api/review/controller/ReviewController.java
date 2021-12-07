package com.evinfo.api.review.controller;

import com.evinfo.api.review.dto.ReviewCreateRequestDto;
import com.evinfo.api.review.dto.ReviewResponseDto;
import com.evinfo.api.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody @Valid final ReviewCreateRequestDto request) {
        final ReviewResponseDto response = reviewService.createReview(request);
        final URI uri = URI.create("/api/reviews/" + response.getId());

        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@RequestParam @NotNull final String stationId) {
        final List<ReviewResponseDto> responses = reviewService.getReviewsByStationId(stationId);

        return ResponseEntity.ok()
                .body(responses);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable @NotNull final Long reviewId) {
        reviewService.deleteReview(reviewId);

        return ResponseEntity.noContent()
                .build();
    }
}
