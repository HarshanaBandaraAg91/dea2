package com.hms.billingservice.dto;

import com.hms.billingservice.entity.BillingStatus;
import com.hms.billingservice.entity.BillingItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingDTO {
    
    private Long id;
    private LocalDateTime billingDate;
    private BigDecimal amount;
    private BillingStatus status;
    private String description;
    private Long referenceId;
    private BillingItemType itemType;
    private Long guestId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
