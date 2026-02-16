package com.crowdfund.backend.campaign.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "campaign_documents",
        indexes = {
                @Index(name = "idx_doc_campaign", columnList = "campaign_id")
        }
)
public class CampaignDocument {

    @Id
    @GeneratedValue
    private UUID id;

    private String filterUrl;

    //  AI confidence score from verification service.
    private Double aiConfidenceScore;

    private  Boolean verificationStatus = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private  Campaign campaign;


    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onUpload(){
        this.uploadedAt = LocalDateTime.now();
    }


    // Getters & Setters
}




/*
-------------------------------------------------------
SUMMARY:
Stores supporting documents for campaign.
Linked via foreign key.
Supports future AI verification system.
-------------------------------------------------------
*/