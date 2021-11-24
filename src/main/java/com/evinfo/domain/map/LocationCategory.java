package com.evinfo.domain.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum LocationCategory {
    CAFE("CE7", "카페"),
    MART("MT1", "대형마트"),
    CONVENIENCE("CS2", "편의점"),
    CULTURE("CT1", "문화시설");

    private final String key;
    private final String name;

    public static LocationCategory getInstance(final String key) {
        return Arrays.stream(LocationCategory.values())
                .filter(locationCategory -> locationCategory.getKey().equals(key))
                .findFirst()
                .orElse(CAFE);
    }
}
