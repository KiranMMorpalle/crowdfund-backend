package com.crowdfund.backend.donation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DonationResponseDTO {

    private UUID donationId;
    private BigDecimal amount;
    private LocalDateTime donatedAt;

    public DonationResponseDTO(UUID donationId, BigDecimal amount, LocalDateTime donatedAt) {
        this.donationId = donationId;
        this.amount = amount;
        this.donatedAt = donatedAt;
    }

    public UUID getDonationId() {
        return donationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDonatedAt() {
        return donatedAt;
    }
}
