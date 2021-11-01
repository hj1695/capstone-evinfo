package com.evinfo.api.charger.service;

import com.evinfo.api.charger.dto.StationRequestDto;
import com.evinfo.api.charger.dto.StationResponseDto;
import com.evinfo.api.charger.repository.StationRepository;
import com.evinfo.domain.charger.Station;
import com.evinfo.utils.ChargerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {
    private static final Double 사용자_위도 = 1.0;
    private static final Double 사용자_경도 = 10.0;
    private static final Long 사용자_요구_데이터_크기 = 1L;
    private static final List<Long> 사용자_필터링_타입 = Arrays.asList(1L, 2L, 3L);

    private StationService stationService;

    @Mock
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationService = new StationService(stationRepository);
    }

    @DisplayName("Station 전체 목록 조회 요청 시 올바르게 수행된다.")
    @Test
    void getStationsTest() {
        List<Station> stations = ChargerGenerator.getStations();
        StationRequestDto request = new StationRequestDto(사용자_위도, 사용자_경도, 사용자_요구_데이터_크기, 사용자_필터링_타입);
        when(stationRepository.findAllJoinFetch()).thenReturn(stations);

        List<StationResponseDto> foundStations = stationService.getStations(request);

        assertThat(foundStations)
                .hasSize(1)
                .extracting("stationId")
                .containsOnly(stations.get(0).getStationId());
    }
}
