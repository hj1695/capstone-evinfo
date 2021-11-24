package com.evinfo.api.map.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LocationResponseDto {
    private final String name;
    private final Double latitude;
    private final Double longitude;
    private final Double distance;
    private final String callNumber;
    private final String address;
    private final String placeUrl;
}
