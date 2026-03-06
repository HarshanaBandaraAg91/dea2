package com.hms.roomreservationservice.repository;

import com.hms.roomreservationservice.model.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomFacilityRepository extends JpaRepository<RoomFacility, Long> {

    boolean existsByName(String name);
}