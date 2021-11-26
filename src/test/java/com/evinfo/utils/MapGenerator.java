package com.evinfo.utils;

import com.evinfo.api.map.dto.LocationCategoryResponseDto;
import com.evinfo.api.map.dto.LocationResponseDto;
import com.evinfo.domain.map.LocationCategory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapGenerator {
    private static final String 가게_이름_1 = "가게 이름 1";
    private static final String 가게_이름_2 = "가게 이름 2";
    private static final String 가게_주소_1 = "가게 주소 2";
    private static final String 가게_주소_2 = "가게 주소 2";
    private static final Double 가게_위도 = 37.0;
    private static final Double 가게_경도 = 127.0;
    private static final Double 가게_거리 = 10.0;
    private static final String 가게_전화번호_1 = "010-1234-5678";
    private static final String 가게_전화번호_2 = "02-1234-5678";
    private static final String 가게_URL_1 = "kakao.com";
    private static final String 가게_URL_2 = "naver.com";
    private static final String 가게_카테고리_1 = "MT1";
    private static final String 가게_카테고리_2 = "CS2";

    private static final LocationResponseDto location1, location2;

    static {
        location1 = LocationResponseDto.builder()
                .name(가게_이름_1)
                .address(가게_주소_1)
                .callNumber(가게_전화번호_1)
                .latitude(가게_위도)
                .longitude(가게_경도)
                .placeUrl(가게_URL_1)
                .distance(가게_거리)
                .category(가게_카테고리_1)
                .build();
        location2 = LocationResponseDto.builder()
                .name(가게_이름_2)
                .address(가게_주소_2)
                .callNumber(가게_전화번호_2)
                .latitude(가게_위도)
                .longitude(가게_경도)
                .placeUrl(가게_URL_2)
                .distance(가게_거리)
                .category(가게_카테고리_2)
                .build();
    }

    public static List<LocationResponseDto> getLocations() {
        return Arrays.asList(location1, location2);
    }

    public static List<LocationCategoryResponseDto> getLocationCategoryResponses() {
        return Arrays.stream(LocationCategory.values())
                .map(LocationCategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}
