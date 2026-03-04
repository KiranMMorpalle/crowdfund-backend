package com.crowdfund.backend.donation.dto;

import com.crowdfund.backend.donation.domain.DonationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DonationResponseDTO {

    private UUID id;
    private BigDecimal amount;
    private DonationStatus status;
    private LocalDateTime donatedAt;

    public DonationResponseDTO(UUID id,
                               BigDecimal amount,
                               DonationStatus status,
                               LocalDateTime donatedAt) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.donatedAt = donatedAt;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public LocalDateTime getDonatedAt() {
        return donatedAt;
    }
}