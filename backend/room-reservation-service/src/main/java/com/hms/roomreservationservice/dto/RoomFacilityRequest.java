package com.hms.roomreservationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomFacilityRequest {

    @NotBlank(message = "Facility name is required")
    private String name;
}