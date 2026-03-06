package com.hms.roomreservationservice.controller;

import com.hms.roomreservationservice.dto.RoomCategoryRequest;
import com.hms.roomreservationservice.dto.RoomCategoryResponse;
import com.hms.roomreservationservice.service.RoomCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-reservation/categories")
@RequiredArgsConstructor
public class RoomCategoryController {

    private final RoomCategoryService service;

    @PostMapping
    public RoomCategoryResponse create(@Valid @RequestBody RoomCategoryRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public RoomCategoryResponse update(@PathVariable Long id, @Valid @RequestBody RoomCategoryRequest request) {
        return service.update(id, request);
    }

    @GetMapping
    public List<RoomCategoryResponse> getAll() {
        return service.getAll();
    }
    
    @GetMapping("/{id}")
    public RoomCategoryResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}