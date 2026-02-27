package com.crowdfund.backend.campaign.controller;

import com.crowdfund.backend.campaign.dto.ApiResponse;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.service.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/campaigns")
public class AdminCampaignController {

    private final CampaignService campaignService;

    public AdminCampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // Temporary: passing adminId manually until JWT integration
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> approve(
            @PathVariable UUID id,
            @RequestParam UUID adminId) {

        CampaignResponseDTO updated =
                campaignService.approveCampaign(id, adminId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign approved successfully", updated)
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> reject(
            @PathVariable UUID id,
            @RequestParam UUID adminId) {

        CampaignResponseDTO updated =
                campaignService.rejectCampaign(id, adminId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign rejected successfully", updated)
        );
    }
}