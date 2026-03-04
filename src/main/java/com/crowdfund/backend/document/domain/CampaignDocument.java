package com.crowdfund.backend.document.domain;

import com.crowdfund.backend.campaign.domain.Campaign;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "campaign_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDocument {

    @Id
    private UUID id;

    private String fileName;

    private String fileType;

    private String fileUrl;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

}