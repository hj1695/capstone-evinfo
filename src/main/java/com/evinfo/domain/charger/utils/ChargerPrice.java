package com.evinfo.domain.charger.utils;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ChargerPrice {
    환경부(292.9, 309.1),
    한국전력공사(292.9, 309.1),
    GS칼텍스(279.0, 279.0),
    제주전기자동차서비스(280.0, 280.0),
    한국전기차충전서비스(255.7, 290.0),
    에스트레픽(292.9, 309.1),
    현대오일뱅크(292.9, 309.1),
    SK에너지(255.0, 255.0),
    차지비(279.0, 279.0),
    기타(292.9, 309.1);

    private final Double lowPrice;
    private final Double highPrice;

    ChargerPrice(Double lowPrice, Double highPrice) {
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    public static Double getPrice(final String businessName, final Long output) {
        var foundChargerPrice = Arrays.stream(ChargerPrice.values())
                .filter(chargerPrice -> chargerPrice.name().equals(businessName))
                .findFirst()
                .orElseGet(() -> ChargerPrice.기타);
        if (output >= 100) {
            return foundChargerPrice.getHighPrice();
        }
        return foundChargerPrice.getLowPrice();
    }
}
