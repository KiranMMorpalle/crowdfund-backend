package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CampaignService {

    CampaignResponseDTO createCampaign(CampaignRequest request);

    CampaignResponseDTO approveCampaign(UUID campaignId, UUID adminId);

    CampaignResponseDTO rejectCampaign(UUID campaignId, UUID adminId);

    List<CampaignResponseDTO> getApprovedCampaigns();

    CampaignResponseDTO getApprovedCampaignById(UUID campaignId);
}