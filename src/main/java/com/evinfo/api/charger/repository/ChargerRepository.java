package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerRepository extends JpaRepository<Charger, ChargerCompositeId> {
}
