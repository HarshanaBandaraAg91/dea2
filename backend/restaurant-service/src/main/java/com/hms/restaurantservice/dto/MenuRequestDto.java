package com.hms.restaurantservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MenuRequestDto {

    private String name;
    private Long quantity;
    private String ingredients;
    private double price;
}
