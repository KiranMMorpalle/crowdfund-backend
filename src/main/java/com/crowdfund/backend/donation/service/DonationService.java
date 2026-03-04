package com.crowdfund.backend.donation.service;

import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.dto.DonationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DonationService {

    /**
     * Step 1:
     * Create donation with status = PENDING
     * Generate UPI payment reference on frontend
     */
    DonationResponseDTO donate(UUID campaignId, UUID userId, DonationRequestDTO request);

    /**
     * Step 2:
     * After user completes UPI payment and clicks confirm
     * Change status to SUCCESS and increment campaign raisedAmount
     */
    DonationResponseDTO confirmDonation(UUID donationId);

    /**
     * View all donations for a campaign
     */
    List<DonationResponseDTO> getDonations(UUID campaignId);

}