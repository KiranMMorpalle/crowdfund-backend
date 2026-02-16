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