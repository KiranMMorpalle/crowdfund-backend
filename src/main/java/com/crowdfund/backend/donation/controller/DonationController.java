package com.crowdfund.backend.donation.controller;

import com.crowdfund.backend.campaign.dto.ApiResponse;
import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.dto.DonationResponseDTO;
import com.crowdfund.backend.donation.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/campaigns")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    /**
     * USER only: Create donation (PENDING)
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{campaignId}/donate")
    public ResponseEntity<ApiResponse<DonationResponseDTO>> donate(
            @PathVariable UUID campaignId,
            @RequestParam UUID userId,
            @RequestBody DonationRequestDTO request) {

        DonationResponseDTO response =
                donationService.donate(campaignId, userId, request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * ADMIN only: Confirm donation (SUCCESS)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/donations/{donationId}/confirm")
    public ResponseEntity<ApiResponse<DonationResponseDTO>> confirmDonation(
            @PathVariable UUID donationId) {

        DonationResponseDTO response =
                donationService.confirmDonation(donationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * ADMIN only: View all donations for campaign
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{campaignId}/donations")
    public ResponseEntity<ApiResponse<List<DonationResponseDTO>>> getDonations(
            @PathVariable UUID campaignId) {

        List<DonationResponseDTO> donations =
                donationService.getDonations(campaignId);

        return ResponseEntity.ok(ApiResponse.success(donations));
    }
}