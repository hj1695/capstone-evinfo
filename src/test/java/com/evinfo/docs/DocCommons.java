package com.evinfo.docs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class DocCommons {
    Map<String, String> errorCodes;
    Map<String, String> chargerStats;
    Map<String, String> chargerPrices;
    Map<String, String> locationCategories;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")

    public DocCommons(Map<String, String> errorCodes, Map<String, String> chargerStats, Map<String, String> chargerPrices, Map<String, String> locationCategories) {
        this.errorCodes = errorCodes;
        this.chargerStats = chargerStats;
        this.chargerPrices = chargerPrices;
        this.locationCategories = locationCategories;
    }
}
