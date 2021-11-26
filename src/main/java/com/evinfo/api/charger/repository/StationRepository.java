package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Station;
import com.evinfo.domain.charger.StationBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, String> {
    @Query("select distinct a from Station a join fetch a.chargers")
    List<Station> findAllJoinFetch();

    @Query("select s.businessName as businessName, count(s) as count from Station s group by businessName order by count(s) desc ")
    List<StationBusiness> findBusinessNameWithCount();
}
