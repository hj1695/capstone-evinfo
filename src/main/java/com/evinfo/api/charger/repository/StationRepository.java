package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, String> {
}
