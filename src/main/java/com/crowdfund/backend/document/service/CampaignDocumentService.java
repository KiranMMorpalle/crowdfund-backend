package com.crowdfund.backend.document.service;

import com.crowdfund.backend.document.domain.CampaignDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CampaignDocumentService {

    CampaignDocument uploadDocument(UUID campaignId, MultipartFile file);

    List<CampaignDocument> getCampaignDocuments(UUID campaignId);

}