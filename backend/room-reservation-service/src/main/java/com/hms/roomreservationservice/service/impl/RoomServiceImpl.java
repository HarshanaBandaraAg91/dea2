package com.hms.roomreservationservice.service.impl;

import com.hms.roomreservationservice.dto.RoomCategoryResponse;
import com.hms.roomreservationservice.dto.RoomFacilityResponse;
import com.hms.roomreservationservice.dto.RoomRequest;
import com.hms.roomreservationservice.dto.RoomResponse;
import com.hms.roomreservationservice.model.Room;
import com.hms.roomreservationservice.model.RoomCategory;
import com.hms.roomreservationservice.model.RoomFacility;
import com.hms.roomreservationservice.repository.RoomCategoryRepository;
import com.hms.roomreservationservice.repository.RoomFacilityRepository;
import com.hms.roomreservationservice.repository.RoomRepository;
import com.hms.roomreservationservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomCategoryRepository categoryRepository;
    private final RoomFacilityRepository facilityRepository;

    @Override
    @Transactional
    public RoomResponse create(RoomRequest request) {
        RoomCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        Set<RoomFacility> facilities = new HashSet<>();
        if (request.getFacilityIds() != null && !request.getFacilityIds().isEmpty()) {
            facilities = new HashSet<>(facilityRepository.findAllById(request.getFacilityIds()));
            if (facilities.size() != request.getFacilityIds().size()) {
                throw new RuntimeException("One or more facilities not found!");
            }
        }

        String roomNumber = generateRoomNumber();

        Room room = Room.builder()
                .roomNumber(roomNumber)
                .pricePerNight(request.getPricePerNight())
                .status(request.getStatus())
                .category(category)
                .facilities(facilities)
                .build();

        Room saved = roomRepository.save(room);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public RoomResponse update(Long id, RoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        RoomCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        room.setPricePerNight(request.getPricePerNight());
        room.setStatus(request.getStatus());
        room.setCategory(category);

        if (request.getFacilityIds() != null) {
            Set<RoomFacility> facilities = new HashSet<>(facilityRepository.findAllById(request.getFacilityIds()));
            if (facilities.size() != request.getFacilityIds().size()) {
                throw new RuntimeException("One or more facilities not found!");
            }
            room.setFacilities(facilities);
        }

        Room updated = roomRepository.save(room);
        return mapToResponse(updated);
    }

    @Override
    public List<RoomResponse> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RoomResponse getById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        return mapToResponse(room);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    private RoomResponse mapToResponse(Room room) {
        Set<RoomFacilityResponse> facilityResponses = room.getFacilities().stream()
                .map(facility -> RoomFacilityResponse.builder()
                        .id(facility.getId())
                        .name(facility.getName())
                        .build())
                .collect(Collectors.toSet());

        RoomCategoryResponse categoryResponse = RoomCategoryResponse.builder()
                .id(room.getCategory().getId())
                .name(room.getCategory().getName())
                .build();

        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .pricePerNight(room.getPricePerNight())
                .status(room.getStatus())
                .category(categoryResponse)
                .facilities(facilityResponses)
                .build();
    }

    private String generateRoomNumber() {
        Optional<Room> lastRoom = roomRepository.findLastRoom();
        if (lastRoom.isPresent()) {
            String lastRoomNumber = lastRoom.get().getRoomNumber();
            int nextNumber = Integer.parseInt(lastRoomNumber.substring(1)) + 1;
            return String.format("R%03d", nextNumber);
        }
        return "R001";
    }
}
