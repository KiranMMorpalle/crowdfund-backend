package com.crowdfund.backend.document.repository;

import com.crowdfund.backend.document.domain.CampaignDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<CampaignDocument, UUID> {

    List<CampaignDocument> findByCampaignId(UUID campaignId);

}