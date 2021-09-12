package com.umjjal.domain.post.web.docs;

import com.umjjal.global.error.ErrorCode;
import com.umjjal.global.error.ErrorResponse;
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
        // TODO: 2021/07/08 추후 공통 Enum이 더 생기게 되면, 여기에 추가해주고 DocCommons에도 추가해준다.

        return ResponseEntity.ok()
                .body(DocCommons.testBuilder()
                        .errorCodes(errorCodes)
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
}
