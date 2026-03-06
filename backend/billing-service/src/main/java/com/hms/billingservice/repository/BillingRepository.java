package com.hms.billingservice.repository;

import com.hms.billingservice.entity.Billing;
import com.hms.billingservice.entity.BillingStatus;
import com.hms.billingservice.entity.BillingItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    
    List<Billing> findByGuestId(Long guestId);
    
    List<Billing> findByStatus(BillingStatus status);
    
    List<Billing> findByItemType(BillingItemType itemType);
    
    List<Billing> findByGuestIdAndStatus(Long guestId, BillingStatus status);
    
    List<Billing> findByBillingDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    Optional<Billing> findByReferenceIdAndItemType(Long referenceId, BillingItemType itemType);
    
    List<Billing> findByStatusAndBillingDateBetween(BillingStatus status, LocalDateTime startDate, LocalDateTime endDate);
    
    List<Billing> findByGuestIdAndItemType(Long guestId, BillingItemType itemType);
}
