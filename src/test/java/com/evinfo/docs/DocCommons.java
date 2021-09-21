package com.evinfo.docs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class DocCommons {
    Map<String, String> errorCodes;
    Map<String, String> chargerTypes;
    Map<String, String> chargerStats;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")

    public DocCommons(Map<String, String> errorCodes, Map<String, String> chargerTypes, Map<String, String> chargerStats) {
        this.errorCodes = errorCodes;
        this.chargerTypes = chargerTypes;
        this.chargerStats = chargerStats;
    }
}
