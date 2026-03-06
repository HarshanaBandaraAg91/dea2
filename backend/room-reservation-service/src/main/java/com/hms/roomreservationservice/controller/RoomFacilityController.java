package com.hms.roomreservationservice.controller;

import com.hms.roomreservationservice.dto.RoomFacilityRequest;
import com.hms.roomreservationservice.dto.RoomFacilityResponse;
import com.hms.roomreservationservice.service.RoomFacilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-reservation/facilities")
@RequiredArgsConstructor
public class RoomFacilityController {

    private final RoomFacilityService service;

    @PostMapping
    public RoomFacilityResponse create(@Valid @RequestBody RoomFacilityRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public RoomFacilityResponse update(@PathVariable Long id, @Valid @RequestBody RoomFacilityRequest request) {
        return service.update(id, request);
    }

    @GetMapping
    public List<RoomFacilityResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RoomFacilityResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}