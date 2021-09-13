package com.evinfo.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("rest")
public class RestProperties {
    private final Map<String, String> evinfo;

    @NotBlank
    public String getEvinfoKey() {
        return evinfo.get("key");
    }

    @NotBlank
    public String getEvinfoUrl() {
        return evinfo.get("url");
    }
}
