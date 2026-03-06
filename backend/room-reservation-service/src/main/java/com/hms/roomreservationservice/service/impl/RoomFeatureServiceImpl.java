package com.hms.roomreservationservice.service.impl;

import com.hms.roomreservationservice.dto.RoomFeatureRequest;
import com.hms.roomreservationservice.dto.RoomFeatureResponse;
import com.hms.roomreservationservice.model.RoomFeature;
import com.hms.roomreservationservice.repository.RoomFeatureRepository;
import com.hms.roomreservationservice.service.RoomFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomFeatureServiceImpl implements RoomFeatureService {

    private final RoomFeatureRepository repository;

    @Override
    @Transactional
    public RoomFeatureResponse create(RoomFeatureRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new RuntimeException("Room Feature already exists!");
        }

        RoomFeature saved = repository.save(
                RoomFeature.builder()
                        .name(request.getName())
                        .build()
        );

        return RoomFeatureResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    @Override
    @Transactional
    public RoomFeatureResponse update(Long id, RoomFeatureRequest request) {
        RoomFeature feature = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Feature not found with id: " + id));

        if (!feature.getName().equals(request.getName()) && repository.existsByName(request.getName())) {
            throw new RuntimeException("Room Feature name already exists!");
        }

        feature.setName(request.getName());
        RoomFeature updated = repository.save(feature);

        return RoomFeatureResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .build();
    }

    @Override
    public List<RoomFeatureResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(feature -> RoomFeatureResponse.builder()
                        .id(feature.getId())
                        .name(feature.getName())
                        .build())
                .toList();
    }

    @Override
    public RoomFeatureResponse getById(Long id) {
        RoomFeature feature = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Feature not found with id: " + id));

        return RoomFeatureResponse.builder()
                .id(feature.getId())
                .name(feature.getName())
                .build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Room Feature not found with id: " + id);
        }
        repository.deleteById(id);
    }
}