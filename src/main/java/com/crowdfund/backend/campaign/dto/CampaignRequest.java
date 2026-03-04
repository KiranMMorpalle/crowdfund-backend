package com.crowdfund.backend.campaign.dto;

import com.crowdfund.backend.campaign.domain.CampaignCategory;

import java.math.BigDecimal;

public class CampaignRequest {

    private String title;
    private String description;
    private BigDecimal targetAmount;

    private CampaignCategory category;
    private String beneficiaryName;
    private String organizerName;
    private String location;

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

    public CampaignCategory getCategory() {
        return category;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public String getLocation() {
        return location;
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

    public void setCategory(CampaignCategory category) {
        this.category = category;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}