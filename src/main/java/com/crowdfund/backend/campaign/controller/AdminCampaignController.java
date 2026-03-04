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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> approve(
            @PathVariable UUID id,
            Authentication authentication) {

        String adminEmail = authentication.getName();

        CampaignResponseDTO updated =
                campaignService.approveCampaign(id, adminEmail);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign approved successfully", updated)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> reject(
            @PathVariable UUID id,
            Authentication authentication) {

        String adminEmail = authentication.getName();

        CampaignResponseDTO updated =
                campaignService.rejectCampaign(id, adminEmail);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign rejected successfully", updated)
        );
    }
}