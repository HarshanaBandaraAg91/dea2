package com.hms.roomreservationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomFeatureRequest {

    @NotBlank(message = "Feature name is required")
    private String name;
}