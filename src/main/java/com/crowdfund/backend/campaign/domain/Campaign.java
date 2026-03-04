package com.crowdfund.backend.campaign.domain;

import com.crowdfund.backend.document.domain.CampaignDocument;
import com.crowdfund.backend.user.domain.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "campaigns",
        indexes = {
                @Index(name = "idx_campaign_status", columnList = "status"),
                @Index(name = "idx_campaign_created_by", columnList = "created_by"),
                @Index(name = "idx_campaign_category", columnList = "category")
        }
)
public class Campaign {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    // NEW: Category (MEDICAL, EDUCATION, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignCategory category;

    // NEW: Beneficiary & Organizer Details
    @Column(nullable = false)
    private String beneficiaryName;

    @Column(nullable = false)
    private String organizerName;

    @Column(nullable = false)
    private String location;

    // UPDATED: Long form story (ImpactGuru style)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    // Financial fields (precision-safe)
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal targetAmount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal raisedAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status = CampaignStatus.PENDING;

    // Cover image path (stored locally)
    private String coverImagePath;

    // Campaign owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // Supporting documents
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignDocument> documents = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Optimistic locking
    @Version
    private Long version;

    public Campaign() {}

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        if (this.raisedAmount == null) {
            this.raisedAmount = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    // ======================
    // Getters and Setters
    // ======================

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

    public CampaignCategory getCategory() {
        return category;
    }

    public void setCategory(CampaignCategory category) {
        this.category = category;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<CampaignDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<CampaignDocument> documents) {
        this.documents = documents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
}