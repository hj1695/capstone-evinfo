package com.evinfo.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("rest")
public class RestProperties {
    private final Map<String, String> evinfo;
    private final Map<String, String> kakao;

    @NotBlank
    public String getEvinfoKey() {
        return evinfo.get("key");
    }

    @NotBlank
    public String getEvinfoBaseUri() {
        return evinfo.get("baseUri");
    }

    @NotBlank
    public String getEvinfoStatusPath() {
        return evinfo.get("statusPath");
    }

    @NotBlank
    public String getEvinfoInfoPath() {
        return evinfo.get("infoPath");
    }

    @NotNull
    public Long getEvinfoChunk() {
        return Long.valueOf(evinfo.get("chunk"));
    }

    @NotNull
    public Long getEvinfoIterator() {
        return Long.valueOf(evinfo.get("iterator"));
    }

    @NotNull
    public Long getEvinfoPeriod() {
        return Long.valueOf(evinfo.get("period"));
    }

    @NotNull
    public String getKakaoUrl() {
        return kakao.get("url");
    }

    @NotNull
    public String getKakaoAuthorization() {
        return kakao.get("authorization");
    }
}
