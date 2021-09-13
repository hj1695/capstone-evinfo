package com.evinfo.global.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RestPropertiesTest {
    @Autowired
    private RestProperties restProperties;

    @DisplayName("Evinfo의 key를 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoKeyTest() {
        final String key = restProperties.getEvinfoKey();

        assertThat(key).isNotBlank();
    }

    @DisplayName("Evinfo의 url을 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoUrlTest() {
        final String url = restProperties.getEvinfoUrl();

        assertThat(url).isNotBlank();
    }
}