package com.evinfo.api.review.repository;

import com.evinfo.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select * from review where station_id = :stationId", nativeQuery = true)
    List<Review> findAllByStationId(String stationId);
}
