package com.crowdfund.backend.campaign.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CampaignResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal raisedAmount;
    private String status;
    private LocalDateTime createdAt;
    private UserSummaryDTO createdBy;

    public CampaignResponseDTO(UUID id,
                               String title,
                               String description,
                               BigDecimal goalAmount,
                               BigDecimal raisedAmount,
                               String status,
                               LocalDateTime createdAt,
                               UserSummaryDTO createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.goalAmount = goalAmount;
        this.raisedAmount = raisedAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public BigDecimal getRaisedAmount() {
        return raisedAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserSummaryDTO getCreatedBy() {
        return createdBy;
    }
}
