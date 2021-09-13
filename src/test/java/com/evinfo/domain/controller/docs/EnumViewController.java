package com.evinfo.domain.controller.docs;

import com.evinfo.domain.charger.domain.ChargerStat;
import com.evinfo.domain.charger.domain.ChargerType;
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
        Map<String, String> chargerTypes = getChargerTypes();
        Map<String, String> chargerStats = getChargerStats();

        return ResponseEntity.ok()
                .body(DocCommons.testBuilder()
                        .errorCodes(errorCodes)
                        .chargerTypes(chargerTypes)
                        .chargerStats(chargerStats)
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

    private Map<String, String> getChargerTypes() {
        Map<String, String> result = new LinkedHashMap<>();
        for (ChargerType chargerType : ChargerType.values()) {
            result.put(chargerType.getKey().toString(), chargerType.getName());
        }
        return result;
    }

    private Map<String, String> getChargerStats() {
        Map<String, String> result = new LinkedHashMap<>();
        for (ChargerStat chargerStat : ChargerStat.values()) {
            result.put(chargerStat.getKey().toString(), chargerStat.getName());
        }
        return result;
    }
}
