package com.hms.roomreservationservice.service.impl;

import com.hms.roomreservationservice.dto.RoomCategoryRequest;
import com.hms.roomreservationservice.dto.RoomCategoryResponse;
import com.hms.roomreservationservice.dto.RoomFeatureResponse;
import com.hms.roomreservationservice.model.RoomCategory;
import com.hms.roomreservationservice.model.RoomFeature;
import com.hms.roomreservationservice.repository.RoomCategoryRepository;
import com.hms.roomreservationservice.repository.RoomFeatureRepository;
import com.hms.roomreservationservice.service.RoomCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomCategoryServiceImpl implements RoomCategoryService {

    private final RoomCategoryRepository repository;
    private final RoomFeatureRepository featureRepository;

    @Override
    @Transactional
    public RoomCategoryResponse create(RoomCategoryRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists!");
        }

        Set<RoomFeature> features = new HashSet<>();
        if (request.getFeatureIds() != null && !request.getFeatureIds().isEmpty()) {
            features = new HashSet<>(featureRepository.findAllById(request.getFeatureIds()));
            if (features.size() != request.getFeatureIds().size()) {
                throw new RuntimeException("One or more features not found!");
            }
        }

        RoomCategory category = RoomCategory.builder()
                .name(request.getName())
                .features(features)
                .build();

        RoomCategory saved = repository.save(category);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public RoomCategoryResponse update(Long id, RoomCategoryRequest request) {
        RoomCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (!category.getName().equals(request.getName()) && repository.existsByName(request.getName())) {
            throw new RuntimeException("Category name already exists!");
        }

        category.setName(request.getName());

        if (request.getFeatureIds() != null) {
            Set<RoomFeature> features = new HashSet<>(featureRepository.findAllById(request.getFeatureIds()));
            if (features.size() != request.getFeatureIds().size()) {
                throw new RuntimeException("One or more features not found!");
            }
            category.setFeatures(features);
        }

        RoomCategory updated = repository.save(category);
        return mapToResponse(updated);
    }

    @Override
    public List<RoomCategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RoomCategoryResponse getById(Long id) {
        RoomCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return mapToResponse(category);
    }

    private RoomCategoryResponse mapToResponse(RoomCategory category) {
        Set<RoomFeatureResponse> featureResponses = category.getFeatures().stream()
                .map(feature -> RoomFeatureResponse.builder()
                        .id(feature.getId())
                        .name(feature.getName())
                        .build())
                .collect(Collectors.toSet());

        return RoomCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .features(featureResponses)
                .build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }
}