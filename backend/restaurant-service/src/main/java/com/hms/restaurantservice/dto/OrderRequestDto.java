package com.hms.restaurantservice.dto;

import com.hms.restaurantservice.model.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class OrderRequestDto {
    private String CustomerName;
    //private List<OrderItem> orderItems=new ArrayList<>();
    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemRequestDto> items;


}
