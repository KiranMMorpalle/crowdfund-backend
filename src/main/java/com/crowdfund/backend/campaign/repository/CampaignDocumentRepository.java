package com.crowdfund.backend.campaign.repository;

import com.crowdfund.backend.document.domain.CampaignDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignDocumentRepository extends JpaRepository<CampaignDocument, UUID> {

}



/*
-------------------------------------------------------
SUMMARY:
Data access layer for campaign documents.
-------------------------------------------------------
*/