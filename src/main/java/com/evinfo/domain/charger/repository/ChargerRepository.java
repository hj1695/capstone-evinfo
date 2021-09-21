package com.evinfo.domain.charger.repository;

import com.evinfo.domain.charger.domain.Charger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerRepository extends JpaRepository<Charger, Long> {
}
