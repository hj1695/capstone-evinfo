package com.evinfo.docs;

import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.utils.ChargerPrice;
import com.evinfo.domain.map.LocationCategory;
import com.evinfo.global.error.ErrorCode;
import com.evinfo.global.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class EnumViewController {

    @GetMapping("/docs")
    public ResponseEntity<DocCommons> findAll() {
        Map<String, String> errorCodes = getErrorCodeDocs();
        Map<String, String> chargerStats = getChargerStats();
        Map<String, String> chargerPrices = getChargerPrices();
        Map<String, String> locationCategories = getLocationCategories();

        return ResponseEntity.ok()
                .body(DocCommons.testBuilder()
                        .errorCodes(errorCodes)
                        .chargerStats(chargerStats)
                        .chargerPrices(chargerPrices)
                        .locationCategories(locationCategories)
                        .build());
    }

    @GetMapping("/docs/error")
    public ResponseEntity<ErrorResponse> getErrorResponse() {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE));
    }

    private Map<String, String> getErrorCodeDocs() {
        Map<String, String> result = new LinkedHashMap<>();
        for (ErrorCode errorCode : ErrorCode.values()) {
            result.put(errorCode.getCode(), errorCode.name());
        }
        return result;
    }

    private Map<String, String> getChargerStats() {
        Map<String, String> result = new LinkedHashMap<>();
        for (ChargerStat chargerStat : ChargerStat.values()) {
            result.put(chargerStat.toString(), chargerStat.getName());
        }
        return result;
    }

    private Map<String, String> getChargerPrices() {
        Map<String, String> result = new LinkedHashMap<>();
        for (ChargerPrice chargerPrice : ChargerPrice.values()) {
            result.put(chargerPrice.toString(), "100kw 이하: " + chargerPrice.getLowPrice() + "\n100kw 이상: " + chargerPrice.getHighPrice());
        }
        return result;
    }

    private Map<String, String> getLocationCategories() {
        Map<String, String> result = new LinkedHashMap<>();
        for (LocationCategory locationCategory : LocationCategory.values()) {
            result.put(locationCategory.getKey(), locationCategory.getName());
        }
        return result;
    }
}
