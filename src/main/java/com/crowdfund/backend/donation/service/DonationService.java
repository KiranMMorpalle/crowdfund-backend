package com.crowdfund.backend.donation.service;

import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.dto.DonationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DonationService {

    DonationResponseDTO donate(UUID campaignId, DonationRequestDTO request);

    List<DonationResponseDTO> getDonations(UUID campaignId);

}
