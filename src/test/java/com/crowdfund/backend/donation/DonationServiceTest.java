package com.crowdfund.backend.donation;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.domain.DonationStatus;
import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.repository.DonationRepository;
import com.crowdfund.backend.donation.service.DonationServiceImpl;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DonationServiceImpl donationService;

    @Test
    void donate_success() {

        UUID campaignId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Campaign campaign = new Campaign();
        campaign.setStatus(com.crowdfund.backend.campaign.domain.CampaignStatus.APPROVED);

        User user = new User();

        DonationRequestDTO request = new DonationRequestDTO();
        request.setAmount(BigDecimal.valueOf(1000));

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(donationRepository.save(any(Donation.class))).thenAnswer(i -> i.getArgument(0));

        var response = donationService.donate(campaignId, userId, request);

        assertNotNull(response);
        assertEquals(DonationStatus.PENDING, response.getStatus());
    }

    @Test
    void donate_campaignNotFound() {

        UUID campaignId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                donationService.donate(campaignId, userId, new DonationRequestDTO()));
    }

    @Test
    void confirmDonation_success() {

        UUID donationId = UUID.randomUUID();

        Campaign campaign = new Campaign();
        campaign.setRaisedAmount(BigDecimal.ZERO);

        Donation donation = new Donation();
        donation.setStatus(DonationStatus.PENDING);
        donation.setAmount(BigDecimal.valueOf(500));
        donation.setCampaign(campaign);

        when(donationRepository.findById(donationId)).thenReturn(Optional.of(donation));
        when(campaignRepository.findById(any())).thenReturn(Optional.of(campaign));

        var response = donationService.confirmDonation(donationId);

        assertEquals(DonationStatus.SUCCESS, response.getStatus());
    }

    @Test
    void confirmDonation_alreadyProcessed() {

        UUID donationId = UUID.randomUUID();

        Donation donation = new Donation();
        donation.setStatus(DonationStatus.SUCCESS);

        when(donationRepository.findById(donationId)).thenReturn(Optional.of(donation));

        assertThrows(RuntimeException.class, () ->
                donationService.confirmDonation(donationId));
    }
}