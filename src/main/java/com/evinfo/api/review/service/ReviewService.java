package com.evinfo.api.review.service;

import com.evinfo.api.charger.repository.StationRepository;
import com.evinfo.api.review.dto.ReviewCreateRequestDto;
import com.evinfo.api.review.dto.ReviewResponseDto;
import com.evinfo.api.review.repository.ReviewRepository;
import com.evinfo.domain.charger.Station;
import com.evinfo.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
