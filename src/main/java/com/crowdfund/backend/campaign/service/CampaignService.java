package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.domain.CampaignCategory;
import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CampaignService {

    CampaignResponseDTO createCampaign(CampaignRequest request, String userEmail);

    CampaignResponseDTO approveCampaign(UUID campaignId, String adminEmail);

    CampaignResponseDTO rejectCampaign(UUID campaignId, String adminEmail);

    CampaignResponseDTO updateCampaign(UUID campaignId, CampaignRequest request, String userEmail);

    void deleteCampaign(UUID campaignId, String adminEmail);

    List<CampaignResponseDTO> getApprovedCampaigns();

    CampaignResponseDTO getApprovedCampaignById(UUID campaignId);

    List<CampaignResponseDTO> getApprovedCampaigns(int page, int size);

    List<CampaignResponseDTO> searchCampaigns(
            String keyword,
            CampaignCategory category,
            int page,
            int size,
            String sortDir
    );
}