package com.crowdfund.backend.campaign.domain;

import com.crowdfund.backend.user.domain.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "campaigns",
        indexes = {
                // Most public queries filter by status.
                @Index(name = "idx_campaign_status", columnList = "status"),

                // For fetching campaigns by creator.
                @Index(name = "idx_campaign_created_by", columnList = "created_by")
        }
)
public class Campaign {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @Column(length = 2000)
    private String description;

    private BigDecimal targetAmount;     // BigDecimal avoids floating precision issues.
    private BigDecimal raisedAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status = CampaignStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)     // LAZY loading improves performance.
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Version    // Optimistic locking => Prevents lost update problem during concurrent donations.
    private  Long version;


    public Campaign() {}

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    // getters and setters


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getRaisedAmount() {
        return raisedAmount;
    }

    public void setRaisedAmount(BigDecimal raisedAmount) {
        this.raisedAmount = raisedAmount;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}









/*
-------------------------------------------------------
SUMMARY:
Core fundraising entity.

Supports:
- Financial tracking
- Approval workflow
- Ownership mapping
- Concurrency control (@Version)

UUID makes it production-ready.
-------------------------------------------------------
*/