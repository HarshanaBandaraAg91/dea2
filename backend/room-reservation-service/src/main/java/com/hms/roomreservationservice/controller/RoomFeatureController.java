package com.hms.roomreservationservice.controller;

import com.hms.roomreservationservice.dto.RoomFeatureRequest;
import com.hms.roomreservationservice.dto.RoomFeatureResponse;
import com.hms.roomreservationservice.service.RoomFeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-reservation/features")
@RequiredArgsConstructor
public class RoomFeatureController {

    private final RoomFeatureService service;

    @PostMapping
    public RoomFeatureResponse create(@Valid @RequestBody RoomFeatureRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public RoomFeatureResponse update(@PathVariable Long id, @Valid @RequestBody RoomFeatureRequest request) {
        return service.update(id, request);
    }

    @GetMapping
    public List<RoomFeatureResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RoomFeatureResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}