package com.umjjal.domain.post.web.docs;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class DocCommons {
    Map<String, String> errorCodes;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public DocCommons(Map<String, String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
