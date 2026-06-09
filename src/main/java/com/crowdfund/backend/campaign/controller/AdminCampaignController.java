package com.crowdfund.backend.campaign.controller;

import com.crowdfund.backend.campaign.dto.ApiResponse;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.service.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/campaigns")
public class AdminCampaignController {

    private final CampaignService campaignService;

    public AdminCampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }



    // write separate admin controller (API) in diff file -> better design
    // useful => when system have more admin APIs


}