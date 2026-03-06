package com.hms.roomreservationservice.config;

import com.hms.roomreservationservice.model.RoomCategory;
import com.hms.roomreservationservice.model.RoomFeature;
import com.hms.roomreservationservice.repository.RoomCategoryRepository;
import com.hms.roomreservationservice.repository.RoomFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(2) // Run after RoomFeatureSeeder
public class RoomCategorySeeder implements CommandLineRunner {

    private final RoomCategoryRepository categoryRepository;
    private final RoomFeatureRepository featureRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            System.out.println("✅ Room Categories already seeded!");
            return;
        }

        // Get all features
        RoomFeature wifi = featureRepository.findAll().stream()
                .filter(f -> f.getName().equals("WIFI")).findFirst().orElse(null);
        RoomFeature ac = featureRepository.findAll().stream()
                .filter(f -> f.getName().equals("AC")).findFirst().orElse(null);
        RoomFeature tv = featureRepository.findAll().stream()
                .filter(f -> f.getName().equals("TV")).findFirst().orElse(null);
        RoomFeature miniBar = featureRepository.findAll().stream()
                .filter(f -> f.getName().equals("MINI_BAR")).findFirst().orElse(null);
        RoomFeature balcony = featureRepository.findAll().stream()
                .filter(f -> f.getName().equals("BALCONY")).findFirst().orElse(null);

        // STANDARD - Basic features
        Set<RoomFeature> standardFeatures = new HashSet<>();
        if (wifi != null) standardFeatures.add(wifi);
        if (tv != null) standardFeatures.add(tv);

        RoomCategory standard = RoomCategory.builder()
                .name("STANDARD")
                .features(standardFeatures)
                .build();
        categoryRepository.save(standard);

        // DELUXE - More features
        Set<RoomFeature> deluxeFeatures = new HashSet<>();
        if (wifi != null) deluxeFeatures.add(wifi);
        if (ac != null) deluxeFeatures.add(ac);
        if (tv != null) deluxeFeatures.add(tv);
        if (miniBar != null) deluxeFeatures.add(miniBar);

        RoomCategory deluxe = RoomCategory.builder()
                .name("DELUXE")
                .features(deluxeFeatures)
                .build();
        categoryRepository.save(deluxe);

        // SUITE - All features
        Set<RoomFeature> suiteFeatures = new HashSet<>();
        if (wifi != null) suiteFeatures.add(wifi);
        if (ac != null) suiteFeatures.add(ac);
        if (tv != null) suiteFeatures.add(tv);
        if (miniBar != null) suiteFeatures.add(miniBar);
        if (balcony != null) suiteFeatures.add(balcony);

        RoomCategory suite = RoomCategory.builder()
                .name("SUITE")
                .features(suiteFeatures)
                .build();
        categoryRepository.save(suite);

        System.out.println("✅ Room Categories Seeded Successfully with Features!");
    }
}