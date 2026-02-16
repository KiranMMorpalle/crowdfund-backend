package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

//    private final CampaignRepository campaignRepository;
//
//    public CampaignServiceImpl(CampaignRepository campaignRepository) {
//        this.campaignRepository = campaignRepository;
//    }
//
//    @Override
//    public Campaign createCampaign(String title, String description, BigDecimal targetAmount) {
//        Campaign campaign = new Campaign(title, description, targetAmount);
//        return campaignRepository.save(campaign);
//    }
//
//    @Override
//    public List<Campaign> getAllCampaigns() {
//        return campaignRepository.findAll();
//    }
}
