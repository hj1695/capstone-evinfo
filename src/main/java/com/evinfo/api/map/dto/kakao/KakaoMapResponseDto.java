package com.evinfo.api.map.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoMapResponseDto {
    private String placeName;
    private Double distance;
    private String placeUrl;
    private String categoryName;
    private String categoryGroupCode;
    private String addressName;
    private String roadAddressName;
    private String id;
    private String phone;
    private Double x;
    private Double y;
}
