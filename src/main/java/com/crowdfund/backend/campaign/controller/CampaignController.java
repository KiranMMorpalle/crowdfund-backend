package com.crowdfund.backend.campaign.controller;

import com.crowdfund.backend.campaign.dto.ApiResponse;
import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.domain.CampaignCategory;
import com.crowdfund.backend.campaign.service.CampaignService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    // =========================
    // CREATE
    // =========================
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createCampaign(
            @RequestBody CampaignRequest request,
            Authentication authentication
    ) {
        String email = (authentication != null) ? authentication.getName() : "test-user";

        CampaignResponseDTO response = campaignService.createCampaign(request, email);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, response));
    }

    // =========================
    // APPROVE
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> approveCampaign(
            @PathVariable UUID id,
            Authentication authentication) {

        String adminEmail = authentication.getName();

        CampaignResponseDTO updated =
                campaignService.approveCampaign(id, adminEmail);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign approved successfully", updated)
        );
    }

    // =========================
    // Reject
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<CampaignResponseDTO>> rejectCampaign(
            @PathVariable UUID id,
            Authentication authentication) {

        String adminEmail = authentication.getName();

        CampaignResponseDTO updated =
                campaignService.rejectCampaign(id, adminEmail);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Campaign rejected successfully", updated)
        );
    }

    // =========================
    // UPDATE
    // =========================
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampaign(
            @PathVariable UUID id,
            @RequestBody CampaignRequest request,
            Authentication authentication
    ) {
        String email = (authentication != null) ? authentication.getName() : "test-user";

        CampaignResponseDTO response = campaignService.updateCampaign(id, request, email);

        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    // =========================
    // DELETE
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCampaign(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        String email = (authentication != null) ? authentication.getName() : "test-user";

        campaignService.deleteCampaign(id, email);

        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully"));
    }



    // =========================
    // GET BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getCampaignById(@PathVariable UUID id) {

        CampaignResponseDTO response = campaignService.getApprovedCampaignById(id);

        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    // =========================
    // 🔥 SEARCH (THIS WAS MISSING)
    // =========================
    @GetMapping
    public ResponseEntity<?> searchCampaigns(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CampaignCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {

        List<CampaignResponseDTO> result =
                campaignService.searchCampaigns(keyword, category, page, size, sort);

        return ResponseEntity.ok(new ApiResponse<>(true, result));
    }
}