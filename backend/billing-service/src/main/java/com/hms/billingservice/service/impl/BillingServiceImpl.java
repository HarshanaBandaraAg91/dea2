package com.hms.billingservice.service.impl;

import com.hms.billingservice.dto.BillingRequest;
import com.hms.billingservice.dto.BillingResponse;
import com.hms.billingservice.entity.Billing;
import com.hms.billingservice.entity.BillingItemType;
import com.hms.billingservice.entity.BillingStatus;
import com.hms.billingservice.repository.BillingRepository;
import com.hms.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BillingServiceImpl implements BillingService {
    
    private final BillingRepository billingRepository;
    
    @Override
    public BillingResponse createBilling(BillingRequest request) {
        Billing billing = Billing.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .referenceId(request.getReferenceId())
                .itemType(request.getItemType())
                .guestId(request.getGuestId())
                .status(BillingStatus.PENDING)
                .billingDate(LocalDateTime.now())
                .build();
        
        Billing savedBilling = billingRepository.save(billing);
        return mapToResponse(savedBilling);
    }
    
    @Override
    public BillingResponse updateBilling(Long id, BillingRequest request) {
        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
        
        billing.setAmount(request.getAmount());
        billing.setDescription(request.getDescription());
        billing.setReferenceId(request.getReferenceId());
        billing.setItemType(request.getItemType());
        billing.setGuestId(request.getGuestId());
        
        Billing updatedBilling = billingRepository.save(billing);
        return mapToResponse(updatedBilling);
    }
    
    @Override
    public BillingResponse getBillingById(Long id) {
        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
        return mapToResponse(billing);
    }
    
    @Override
    public List<BillingResponse> getAll() {
        return billingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BillingResponse> getByGuestId(Long guestId) {
        return billingRepository.findByGuestId(guestId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BillingResponse> getByStatus(BillingStatus status) {
        return billingRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BillingResponse> getByItemType(BillingItemType itemType) {
        return billingRepository.findByItemType(itemType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BillingResponse> getByGuestIdAndStatus(Long guestId, BillingStatus status) {
        return billingRepository.findByGuestIdAndStatus(guestId, status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BillingResponse> getByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return billingRepository.findByBillingDateBetween(startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BillingResponse> getByStatusAndDateRange(BillingStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        return billingRepository.findByStatusAndBillingDateBetween(status, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public BillingResponse updateBillingStatus(Long id, BillingStatus status) {
        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
        billing.setStatus(status);
        Billing updatedBilling = billingRepository.save(billing);
        return mapToResponse(updatedBilling);
    }
    
    @Override
    public void deleteBilling(Long id) {
        if (!billingRepository.existsById(id)) {
            throw new RuntimeException("Billing not found with id: " + id);
        }
        billingRepository.deleteById(id);
    }
    
    @Override
    public List<BillingResponse> getByGuestIdAndItemType(Long guestId, BillingItemType itemType) {
        return billingRepository.findByGuestIdAndItemType(guestId, itemType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private BillingResponse mapToResponse(Billing billing) {
        return BillingResponse.builder()
                .id(billing.getId())
                .billingDate(billing.getBillingDate())
                .amount(billing.getAmount())
                .status(billing.getStatus())
                .description(billing.getDescription())
                .referenceId(billing.getReferenceId())
                .itemType(billing.getItemType())
                .guestId(billing.getGuestId())
                .createdAt(billing.getCreatedAt())
                .updatedAt(billing.getUpdatedAt())
                .build();
    }
}
