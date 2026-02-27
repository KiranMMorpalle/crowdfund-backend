package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignStatus;
import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.dto.UserSummaryDTO;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.common.exception.BusinessValidationException;
import com.crowdfund.backend.common.exception.ResourceNotFoundException;
import com.crowdfund.backend.common.exception.UnauthorizedOperationException;
import com.crowdfund.backend.user.domain.Role;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository,
                               UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    // 1️⃣ CREATE CAMPAIGN
    @Override
    @Transactional
    public CampaignResponseDTO createCampaign(CampaignRequest request) {

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getTargetAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessValidationException("Target amount must be greater than zero.");
        }

        Campaign campaign = new Campaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setTargetAmount(request.getTargetAmount());
        campaign.setRaisedAmount(BigDecimal.ZERO);
        campaign.setCreatedBy(creator);
        campaign.setStatus(CampaignStatus.PENDING);

        Campaign saved = campaignRepository.save(campaign);

        return mapToDTO(saved);
    }

    // 2️⃣ APPROVE CAMPAIGN
    @Override
    @Transactional
    public CampaignResponseDTO approveCampaign(UUID campaignId, UUID adminId) {

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found."));

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only ADMIN can approve campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found."));

        if (!campaign.getStatus().equals(CampaignStatus.PENDING)) {
            throw new BusinessValidationException("Only PENDING campaign can be approved.");
        }

        campaign.setStatus(CampaignStatus.APPROVED);

        Campaign updated = campaignRepository.save(campaign);

        return mapToDTO(updated);
    }

    // 3️⃣ REJECT CAMPAIGN
    @Override
    @Transactional
    public CampaignResponseDTO rejectCampaign(UUID campaignId, UUID adminId) {

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found."));

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only ADMIN can reject campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found."));

        if (!campaign.getStatus().equals(CampaignStatus.PENDING)) {
            throw new BusinessValidationException("Only PENDING campaign can be rejected.");
        }

        campaign.setStatus(CampaignStatus.REJECTED);

        Campaign updated = campaignRepository.save(campaign);

        return mapToDTO(updated);
    }

    // 4️⃣ GET ALL APPROVED CAMPAIGNS
    @Override
    public List<CampaignResponseDTO> getApprovedCampaigns() {

        List<Campaign> campaigns =
                campaignRepository.findByStatus(CampaignStatus.APPROVED);

        return campaigns.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // 5️⃣ GET APPROVED CAMPAIGN BY ID
    @Override
    public CampaignResponseDTO getApprovedCampaignById(UUID campaignId) {

        Campaign campaign = campaignRepository
                .findByIdAndStatus(campaignId, CampaignStatus.APPROVED)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        return mapToDTO(campaign);
    }

    // 🔁 ENTITY → DTO MAPPER
    private CampaignResponseDTO mapToDTO(Campaign campaign) {

        UserSummaryDTO userDTO = new UserSummaryDTO(
                campaign.getCreatedBy().getId(),
                campaign.getCreatedBy().getName()
        );

        return new CampaignResponseDTO(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getDescription(),
                campaign.getTargetAmount(),
                campaign.getRaisedAmount(),
                campaign.getStatus().name(),
                campaign.getCreatedAt(),
                userDTO
        );
    }
}