package com.hms.roomreservationservice.service.impl;

import com.hms.roomreservationservice.dto.RoomFacilityRequest;
import com.hms.roomreservationservice.dto.RoomFacilityResponse;
import com.hms.roomreservationservice.model.RoomFacility;
import com.hms.roomreservationservice.repository.RoomFacilityRepository;
import com.hms.roomreservationservice.service.RoomFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomFacilityServiceImpl implements RoomFacilityService {

    private final RoomFacilityRepository repository;

    @Override
    @Transactional
    public RoomFacilityResponse create(RoomFacilityRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new RuntimeException("Facility already exists!");
        }

        RoomFacility saved = repository.save(
                new RoomFacility(null, request.getName())
        );

        return RoomFacilityResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    @Override
    @Transactional
    public RoomFacilityResponse update(Long id, RoomFacilityRequest request) {
        RoomFacility facility = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));

        if (!facility.getName().equals(request.getName()) && repository.existsByName(request.getName())) {
            throw new RuntimeException("Facility name already exists!");
        }

        facility.setName(request.getName());
        RoomFacility updated = repository.save(facility);

        return RoomFacilityResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .build();
    }

    @Override
    public List<RoomFacilityResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(f -> RoomFacilityResponse.builder()
                        .id(f.getId())
                        .name(f.getName())
                        .build())
                .toList();
    }

    @Override
    public RoomFacilityResponse getById(Long id) {
        RoomFacility facility = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));

        return RoomFacilityResponse.builder()
                .id(facility.getId())
                .name(facility.getName())
                .build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Facility not found with id: " + id);
        }
        repository.deleteById(id);
    }
}