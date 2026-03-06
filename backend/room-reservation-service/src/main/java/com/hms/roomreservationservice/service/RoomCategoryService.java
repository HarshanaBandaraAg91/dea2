package com.hms.roomreservationservice.service;

import com.hms.roomreservationservice.dto.RoomCategoryRequest;
import com.hms.roomreservationservice.dto.RoomCategoryResponse;

import java.util.List;

public interface RoomCategoryService {

    RoomCategoryResponse create(RoomCategoryRequest request);

    RoomCategoryResponse update(Long id, RoomCategoryRequest request);

    List<RoomCategoryResponse> getAll();
    
    RoomCategoryResponse getById(Long id);

    void delete(Long id);
}