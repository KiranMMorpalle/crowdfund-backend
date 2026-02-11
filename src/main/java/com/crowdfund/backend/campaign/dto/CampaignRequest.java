package com.crowdfund.backend.campaign.dto;

import java.math.BigDecimal;

public class CampaignRequest {

    private String title;
    private String description;
    private BigDecimal targetAmount;

    public CampaignRequest() {}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }
}
