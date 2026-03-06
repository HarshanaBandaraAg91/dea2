package com.hms.restaurantservice.repository;

import com.hms.restaurantservice.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);
}
