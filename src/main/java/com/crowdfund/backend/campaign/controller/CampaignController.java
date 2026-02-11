package com.crowdfund.backend.campaign.controller;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping
    public Campaign createCampaign(@RequestBody CampaignRequest request) {
        return campaignService.createCampaign(
                request.getTitle(),
                request.getDescription(),
                request.getTargetAmount()
        );
    }


    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }
}
