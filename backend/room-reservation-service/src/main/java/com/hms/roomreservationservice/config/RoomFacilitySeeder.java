package com.hms.roomreservationservice.config;

import com.hms.roomreservationservice.model.RoomFacility;
import com.hms.roomreservationservice.repository.RoomFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomFacilitySeeder implements CommandLineRunner {

    private final RoomFacilityRepository repository;

    @Override
    public void run(String... args) {

        if (!repository.existsByName("GYM"))
            repository.save(new RoomFacility(null, "GYM"));

        if (!repository.existsByName("SWIMMING_POOL"))
            repository.save(new RoomFacility(null, "SWIMMING_POOL"));

        if (!repository.existsByName("SPA"))
            repository.save(new RoomFacility(null, "SPA"));

        if (!repository.existsByName("PARKING"))
            repository.save(new RoomFacility(null, "PARKING"));

        System.out.println("✅ Room Facilities Seeded Successfully!");
    }
}