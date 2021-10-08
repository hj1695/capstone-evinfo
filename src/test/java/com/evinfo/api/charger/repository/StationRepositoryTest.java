package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.Station;
import com.evinfo.utils.ChargerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@SpringBootTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;
    private Station station1;
    private Station station2;
    private Charger charger1;
    private Charger charger2;

    @BeforeEach
    void setUp() {
        stationRepository.deleteAll();

        station1 = ChargerGenerator.getStations().get(0);
        station2 = ChargerGenerator.getStations().get(1);
        charger1 = ChargerGenerator.getChargers().get(0);
        charger2 = ChargerGenerator.getChargers().get(1);
        station1.addCharger(charger1);
        station2.addCharger(charger2);

        station1 = stationRepository.save(station1);
        station2 = stationRepository.save(station2);
    }

    @DisplayName("findAllByPosition이 올바르게 수행된다.")
    @Test
    void findAllByPositionTest() {
        List<Station> stations = stationRepository.findAllByPosition(station1.getLatitude(), station1.getLongitude(), 1L);

        assertThat(stationRepository.findAll()).hasSize(2);
        assertThat(stations).hasSize(1);
        assertThat(stations.get(0).getStationId()).isEqualTo(station1.getStationId());
        assertThat(stations.get(0).getStationName()).isEqualTo(station1.getStationName());
    }
}
