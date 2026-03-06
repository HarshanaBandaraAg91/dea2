package com.hms.billingservice.service;

import com.hms.billingservice.dto.BillingRequest;
import com.hms.billingservice.dto.BillingResponse;
import com.hms.billingservice.entity.BillingStatus;
import com.hms.billingservice.entity.BillingItemType;

import java.time.LocalDateTime;
import java.util.List;

public interface BillingService {

    BillingResponse createBilling(BillingRequest request);

    BillingResponse updateBilling(Long id, BillingRequest request);

    BillingResponse getBillingById(Long id);

    List<BillingResponse> getAll();

    List<BillingResponse> getByGuestId(Long guestId);

    List<BillingResponse> getByStatus(BillingStatus status);

    List<BillingResponse> getByItemType(BillingItemType itemType);

    List<BillingResponse> getByGuestIdAndStatus(Long guestId, BillingStatus status);

    List<BillingResponse> getByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<BillingResponse> getByStatusAndDateRange(BillingStatus status, LocalDateTime startDate, LocalDateTime endDate);

    BillingResponse updateBillingStatus(Long id, BillingStatus status);

    void deleteBilling(Long id);

    List<BillingResponse> getByGuestIdAndItemType(Long guestId, BillingItemType itemType);
}
