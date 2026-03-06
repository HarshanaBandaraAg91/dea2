package com.hms.billingservice.dto;

import com.hms.billingservice.entity.BillingItemType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Reference ID is required")
    private Long referenceId;

    @NotNull(message = "Item type is required")
    private BillingItemType itemType;

    @NotNull(message = "Guest ID is required")
    private Long guestId;
}
