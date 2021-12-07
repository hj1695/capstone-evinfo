package com.evinfo.api.charger.dto;

import com.evinfo.api.review.dto.ReviewResponseDto;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.Station;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StationResponseDto {
    private final String stationName;
    private final String stationId;
    private final String address;
    private final String location;
    private final String useTime;
    private final Double latitude;
    private final Double longitude;
    private final String callNumber;
    private final String businessName;
    private final Double distance;
    private final Integer enableChargers;
    private final Boolean isLimit;
    private final Boolean isParkingFree;
    private final List<ChargerResponseDto> chargers;
    private final Double reviewAverage;
    private final List<ReviewResponseDto> reviews;

    public StationResponseDto(final Station station, final Double distance) {
        this.stationName = station.getStationName();
        this.stationId = station.getStationId();
        this.address = station.getAddress();
        this.location = station.getLocation();
        this.useTime = station.getUseTime();
        this.latitude = station.getLatitude();
        this.longitude = station.getLongitude();
        this.callNumber = station.getCallNumber();
        this.businessName = station.getBusinessName();
        this.isLimit = station.getIsLimit();
        this.isParkingFree = station.getIsParkingFree();
        this.distance = distance;
        this.chargers = station.getChargers()
                .stream()
                .map(ChargerResponseDto::new)
                .collect(Collectors.toList());
        this.reviews = station.getReviews() // TODO: 2021/12/07 여기서 정렬 필요할지 고민해보기
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
        if (this.reviews.isEmpty()) {
            this.reviewAverage = 0.0;
        } else {
            this.reviewAverage = reviews.stream()
                    .collect(Collectors.averagingDouble(ReviewResponseDto::getStar));
        }
        this.enableChargers = (int) station.getChargers()
                .stream()
                .filter(charger -> charger.getChargerStat().equals(ChargerStat.WAITING))
                .count();
    }
}
