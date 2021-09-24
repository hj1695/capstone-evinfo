package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Charger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerRepository extends JpaRepository<Charger, Long> {
}
