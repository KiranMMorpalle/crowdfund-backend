package com.crowdfund.backend.campaign.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private BigDecimal targetAmount;

    private BigDecimal raisedAmount;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    public Campaign() {
    }

    public Campaign(String title, String description, BigDecimal targetAmount) {
        this.title = title;
        this.description = description;
        this.targetAmount = targetAmount;
        this.raisedAmount = BigDecimal.ZERO;
        this.status = CampaignStatus.PENDING;
    }

    // getters and setters
}
