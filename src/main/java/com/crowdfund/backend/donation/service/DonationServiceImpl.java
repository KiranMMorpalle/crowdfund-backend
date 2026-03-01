package com.crowdfund.backend.donation.service;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignStatus;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.common.exception.BusinessValidationException;
import com.crowdfund.backend.common.exception.ResourceNotFoundException;
import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.dto.DonationResponseDTO;
import com.crowdfund.backend.donation.repository.DonationRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonationServiceImpl implements DonationService {

    private final CampaignRepository campaignRepository;
    private final DonationRepository donationRepository;

    public DonationServiceImpl(CampaignRepository campaignRepository,
                               DonationRepository donationRepository) {
        this.campaignRepository = campaignRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    @Transactional
    public DonationResponseDTO donate(UUID campaignId, DonationRequestDTO request) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

//        try {
//            Thread.sleep(5000); // 5 seconds delay for testing
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        if (campaign.getStatus() != CampaignStatus.APPROVED) {
            throw new BusinessValidationException("Campaign not open for donations");
        }

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessValidationException("Donation amount must be positive");
        }

        // SAFE increment (optimistic locking will protect)
        campaign.setRaisedAmount(
                campaign.getRaisedAmount().add(request.getAmount())
        );

        Donation donation = new Donation();
        donation.setCampaign(campaign);
        donation.setAmount(request.getAmount());
        donation.setDonatedAt(LocalDateTime.now());

        donationRepository.save(donation);

        return new DonationResponseDTO(
                donation.getId(),
                donation.getAmount(),
                donation.getDonatedAt()
        );
    }

    @Override
    public List<DonationResponseDTO> getDonations(UUID campaignId) {

        return donationRepository.findByCampaignId(campaignId)
                .stream()
                .map(d -> new DonationResponseDTO(
                        d.getId(),
                        d.getAmount(),
                        d.getDonatedAt()))
                .collect(Collectors.toList());
    }
}