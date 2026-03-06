package com.hms.roomreservationservice.config;

import com.hms.roomreservationservice.model.RoomFeature;
import com.hms.roomreservationservice.repository.RoomFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1) // Run first before RoomCategorySeeder
public class RoomFeatureSeeder implements CommandLineRunner {

    private final RoomFeatureRepository repository;

    @Override
    public void run(String... args) {

        if (!repository.existsByName("WIFI"))
            repository.save(new RoomFeature(null, "WIFI"));

        if (!repository.existsByName("AC"))
            repository.save(new RoomFeature(null, "AC"));

        if (!repository.existsByName("TV"))
            repository.save(new RoomFeature(null, "TV"));

        if (!repository.existsByName("MINI_BAR"))
            repository.save(new RoomFeature(null, "MINI_BAR"));

        if (!repository.existsByName("BALCONY"))
            repository.save(new RoomFeature(null, "BALCONY"));

        System.out.println("✅ Room Features Seeded Successfully!");
    }
}