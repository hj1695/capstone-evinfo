package com.evinfo.api.review.service;

import com.evinfo.api.charger.repository.StationRepository;
import com.evinfo.api.review.dto.ReviewCreateRequestDto;
import com.evinfo.api.review.dto.ReviewResponseDto;
import com.evinfo.api.review.repository.ReviewRepository;
import com.evinfo.domain.charger.Station;
import com.evinfo.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StationRepository stationRepository;

    public ReviewResponseDto createReview(final ReviewCreateRequestDto request) {
        final Station station = stationRepository.findById(request.getStationId())
                .orElseThrow(IllegalArgumentException::new);
        final Review review = new Review(request.getContent(), request.getStar(), station);

        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    public List<ReviewResponseDto> getReviewsByStationId(final String stationId) {
        List<Review> reviews = reviewRepository.findAllByStationId(stationId);

        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 리뷰 ID 값입니다."));

        reviewRepository.delete(review);
    }
}
