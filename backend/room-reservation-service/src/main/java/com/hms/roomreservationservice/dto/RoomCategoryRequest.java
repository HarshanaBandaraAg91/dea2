package com.hms.roomreservationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomCategoryRequest {

    @NotBlank(message = "Category name is required")
    private String name;
    
    private Set<Long> featureIds;
}