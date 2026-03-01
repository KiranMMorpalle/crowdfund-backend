package com.crowdfund.backend.donation.dto;

import java.math.BigDecimal;

public class DonationRequestDTO {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
