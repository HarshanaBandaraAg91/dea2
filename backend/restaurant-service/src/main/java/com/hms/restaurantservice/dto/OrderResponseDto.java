package com.hms.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private BigDecimal totalAmount;
    //private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> orderItems;
}
