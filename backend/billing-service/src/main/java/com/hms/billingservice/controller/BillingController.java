package com.hms.billingservice.controller;

import com.hms.billingservice.dto.BillingRequest;
import com.hms.billingservice.dto.BillingResponse;
import com.hms.billingservice.entity.BillingStatus;
import com.hms.billingservice.entity.BillingItemType;
import com.hms.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/billings")
@RequiredArgsConstructor
public class BillingController {
    
    private final BillingService billingService;
    
    @PostMapping
    public ResponseEntity<BillingResponse> createBilling(@RequestBody BillingRequest request) {
        BillingResponse billing = billingService.createBilling(request);
        return new ResponseEntity<>(billing, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BillingResponse> getBillingById(@PathVariable Long id) {
        BillingResponse billing = billingService.getBillingById(id);
        return ResponseEntity.ok(billing);
    }
    
    @GetMapping
    public ResponseEntity<List<BillingResponse>> getAllBillings() {
        List<BillingResponse> billings = billingService.getAll();
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<BillingResponse>> getBillingByGuestId(@PathVariable Long guestId) {
        List<BillingResponse> billings = billingService.getByGuestId(guestId);
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillingResponse>> getBillingByStatus(@PathVariable BillingStatus status) {
        List<BillingResponse> billings = billingService.getByStatus(status);
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/item-type/{itemType}")
    public ResponseEntity<List<BillingResponse>> getBillingByItemType(@PathVariable BillingItemType itemType) {
        List<BillingResponse> billings = billingService.getByItemType(itemType);
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/guest/{guestId}/status/{status}")
    public ResponseEntity<List<BillingResponse>> getBillingByGuestIdAndStatus(
            @PathVariable Long guestId,
            @PathVariable BillingStatus status) {
        List<BillingResponse> billings = billingService.getByGuestIdAndStatus(guestId, status);
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/guest/{guestId}/item-type/{itemType}")
    public ResponseEntity<List<BillingResponse>> getBillingByGuestIdAndItemType(
            @PathVariable Long guestId,
            @PathVariable BillingItemType itemType) {
        List<BillingResponse> billings = billingService.getByGuestIdAndItemType(guestId, itemType);
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<BillingResponse>> getBillingByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<BillingResponse> billings = billingService.getByDateRange(startDate, endDate);
        return ResponseEntity.ok(billings);
    }
    
    @GetMapping("/status/{status}/date-range")
    public ResponseEntity<List<BillingResponse>> getBillingByStatusAndDateRange(
            @PathVariable BillingStatus status,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<BillingResponse> billings = billingService.getByStatusAndDateRange(status, startDate, endDate);
        return ResponseEntity.ok(billings);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BillingResponse> updateBilling(
            @PathVariable Long id,
            @RequestBody BillingRequest request) {
        BillingResponse billing = billingService.updateBilling(id, request);
        return ResponseEntity.ok(billing);
    }
    
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<BillingResponse> updateBillingStatus(
            @PathVariable Long id,
            @PathVariable BillingStatus status) {
        BillingResponse billing = billingService.updateBillingStatus(id, status);
        return ResponseEntity.ok(billing);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        billingService.deleteBilling(id);
        return ResponseEntity.noContent().build();
    }
}

