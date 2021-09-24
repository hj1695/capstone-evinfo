package com.evinfo.api.charger.service;

import com.evinfo.api.charger.dto.ChargerRequestDto;
import com.evinfo.api.charger.dto.ChargerResponseDto;
import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.domain.charger.Charger;
import com.evinfo.utils.ChargerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChargerServiceTest {
    private ChargerService chargerService;

    @Mock
    private ChargerRepository chargerRepository;

    @BeforeEach
    void setUp() {
        chargerService = new ChargerService(chargerRepository);
    }

    @DisplayName("Charger 전체 목록 조회 요청 시 올바르게 수행된다.")
    @Test
    void getChargersTest() {
        List<Charger> chargers = ChargerGenerator.getChargers();
        ChargerRequestDto request = new ChargerRequestDto(1.0, 10.0, 1L);
        when(chargerRepository.findAll()).thenReturn(chargers);

        List<ChargerResponseDto> foundChargers = chargerService.getChargers(request);

        assertThat(foundChargers)
                .hasSize(1)
                .extracting("stationId")
                .containsOnly(chargers.get(0).getStationId());
    }

}
