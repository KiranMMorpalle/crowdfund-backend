package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.domain.Campaign;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CampaignService {

    Campaign createCampaign(String title, String description, BigDecimal targetAmount, UUID creatorId);
    Campaign approveCampaign(UUID campaignId, UUID adminId);
    Campaign regectCampaign(UUID campaignId, UUID adminId);


    List<Campaign> getAllCampaigns();
    List<Campaign> getApprovedCampaigns();
}
