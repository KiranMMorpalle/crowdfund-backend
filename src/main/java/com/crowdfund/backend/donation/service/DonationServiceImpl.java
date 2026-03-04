package com.crowdfund.backend.donation.service;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.common.exception.BusinessValidationException;
import com.crowdfund.backend.common.exception.ResourceNotFoundException;
import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.domain.DonationStatus;
import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.dto.DonationResponseDTO;
import com.crowdfund.backend.donation.repository.DonationRepository;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public DonationServiceImpl(DonationRepository donationRepository,
                               CampaignRepository campaignRepository,
                               UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public DonationResponseDTO donate(UUID campaignId,
                                      UUID userId,
                                      DonationRequestDTO request) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!"APPROVED".equals(campaign.getStatus().name())) {
            throw new BusinessValidationException("Campaign is not approved for donations");
        }

        Donation donation = new Donation();
        donation.setCampaign(campaign);
        donation.setUser(user);
        donation.setAmount(request.getAmount());
        donation.setStatus(DonationStatus.PENDING);
        donation.setDonatedAt(LocalDateTime.now());

        donationRepository.save(donation);

        return new DonationResponseDTO(
                donation.getId(),
                donation.getAmount(),
                donation.getStatus(),
                donation.getDonatedAt()
        );
    }

    @Override
    @Transactional
    public DonationResponseDTO confirmDonation(UUID donationId) {

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found"));

        if (donation.getStatus() != DonationStatus.PENDING) {
            throw new BusinessValidationException("Donation already processed");
        }

        Campaign campaign = campaignRepository.findById(
                donation.getCampaign().getId()
        ).orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        campaign.setRaisedAmount(
                campaign.getRaisedAmount().add(donation.getAmount())
        );

        donation.setStatus(DonationStatus.SUCCESS);

        return new DonationResponseDTO(
                donation.getId(),
                donation.getAmount(),
                donation.getStatus(),
                donation.getDonatedAt()
        );
    }

    @Override
    public List<DonationResponseDTO> getDonations(UUID campaignId) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        List<Donation> donations = donationRepository.findByCampaign(campaign);

        return donations.stream()
                .map(donation -> new DonationResponseDTO(
                        donation.getId(),
                        donation.getAmount(),
                        donation.getStatus(),
                        donation.getDonatedAt()
                ))
                .collect(Collectors.toList());
    }
}