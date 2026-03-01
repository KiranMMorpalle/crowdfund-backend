package com.crowdfund.backend.donation.controller;

//import com.crowdfund.backend.common.response.ApiResponse;
import com.crowdfund.backend.campaign.dto.ApiResponse;
import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.dto.DonationResponseDTO;
import com.crowdfund.backend.donation.service.DonationService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{id}/donate")
    public ResponseEntity<ApiResponse<DonationResponseDTO>> donate(
            @PathVariable UUID id,
            @RequestBody DonationRequestDTO request) {

        DonationResponseDTO response = donationService.donate(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}/donations")
    public ResponseEntity<ApiResponse<List<DonationResponseDTO>>> getDonations(
            @PathVariable UUID id) {

        List<DonationResponseDTO> donations = donationService.getDonations(id);
        return ResponseEntity.ok(ApiResponse.success(donations));
    }
}