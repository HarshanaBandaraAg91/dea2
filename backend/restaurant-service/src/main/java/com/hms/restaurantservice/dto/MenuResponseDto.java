package com.hms.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {
    private Long id;
    private String name;
    private Long quantity;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
