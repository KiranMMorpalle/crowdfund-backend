package com.crowdfund.backend.campaign.controller;

import com.crowdfund.backend.campaign.dto.*;
import com.crowdfund.backend.campaign.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignResponseDTO>>> getApprovedCampaigns() {

        List<CampaignResponseDTO> campaigns =
                campaignService.getApprovedCampaigns();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaigns fetched successfully", campaigns)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> getById(@PathVariable UUID id) {

        CampaignResponseDTO campaign =
                campaignService.getApprovedCampaignById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign fetched successfully", campaign)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> createCampaign(
            @RequestBody CampaignRequest request) {

        CampaignResponseDTO created =
                campaignService.createCampaign(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Campaign created successfully", created));
    }
}