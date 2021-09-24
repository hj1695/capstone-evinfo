package com.evinfo.global.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles(profiles = "test")
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

    @DisplayName("Evinfo의 Base Uri을 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoBaseUriTest() {
        final String uri = restProperties.getEvinfoBaseUri();

        assertThat(uri).isNotBlank();
    }

    @DisplayName("Evinfo의 statusPath를 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoStatusPathTest() {
        final String uri = restProperties.getEvinfoStatusPath();

        assertThat(uri).isNotBlank();
    }

    @DisplayName("Evinfo의 infoPath를 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoInfoPathTest() {
        final String uri = restProperties.getEvinfoInfoPath();

        assertThat(uri).isNotBlank();
    }

    @DisplayName("Evinfo의 chunk를 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoChunkTest() {
        final Long chunk = restProperties.getEvinfoChunk();

        assertThat(chunk).isNotNull();
    }

    @DisplayName("Evinfo의 iterator를 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoIteratorTest() {
        final Long iterator = restProperties.getEvinfoIterator();

        assertThat(iterator).isNotNull();
    }

    @DisplayName("Evinfo의 period를 가져오는 동작이 올바르게 수행된다.")
    @Test
    void getEvinfoPeriodTest() {
        final Long iterator = restProperties.getEvinfoPeriod();

        assertThat(iterator).isNotNull();
    }
}