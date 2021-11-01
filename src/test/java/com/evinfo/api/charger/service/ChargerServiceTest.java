package com.evinfo.api.charger.service;


import com.evinfo.api.charger.dto.ChargerTypeResponseDto;
import com.evinfo.utils.ChargerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class ChargerServiceTest {
    private ChargerService chargerService;

    @BeforeEach
    void setUp() {
        chargerService = new ChargerService();
    }

    @DisplayName("Charger 타입 목록 조회 요청 시 올바르게 수행된다.")
    @Test
    void getChargersTypeTest() {
        List<ChargerTypeResponseDto> responses = ChargerGenerator.getChargerTypeResponses();

        List<ChargerTypeResponseDto> foundResponses = chargerService.getChargerTypes();

        assertThat(foundResponses)
                .hasSize(8)
                .extracting("key")
                .containsExactly(
                        responses.get(0).getKey(),
                        responses.get(1).getKey(),
                        responses.get(2).getKey(),
                        responses.get(3).getKey(),
                        responses.get(4).getKey(),
                        responses.get(5).getKey(),
                        responses.get(6).getKey(),
                        responses.get(7).getKey()
                );
    }
}
