package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignCategory;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


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

    @Override
    @Transactional
    public CampaignResponseDTO createCampaign(CampaignRequest request, String userEmail) {

        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getTargetAmount() == null ||
                request.getTargetAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessValidationException("Target amount must be greater than zero.");
        }

        if (request.getCategory() == null ||
                request.getBeneficiaryName() == null ||
                request.getOrganizerName() == null ||
                request.getLocation() == null) {
            throw new BusinessValidationException("All mandatory fields must be provided.");
        }

        Campaign campaign = new Campaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setTargetAmount(request.getTargetAmount());
        campaign.setRaisedAmount(BigDecimal.ZERO);

        campaign.setCategory(request.getCategory());
        campaign.setBeneficiaryName(request.getBeneficiaryName());
        campaign.setOrganizerName(request.getOrganizerName());
        campaign.setLocation(request.getLocation());

        campaign.setCreatedBy(creator);
        campaign.setStatus(CampaignStatus.PENDING);

        Campaign saved = campaignRepository.save(campaign);

        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public CampaignResponseDTO approveCampaign(UUID campaignId, String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only ADMIN can approve campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        if (!campaign.getStatus().equals(CampaignStatus.PENDING)) {
            throw new BusinessValidationException("Only PENDING campaign can be approved.");
        }

        campaign.setStatus(CampaignStatus.APPROVED);

        return mapToDTO(campaignRepository.save(campaign));
    }

    @Override
    @Transactional
    public CampaignResponseDTO rejectCampaign(UUID campaignId, String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only ADMIN can reject campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        if (!campaign.getStatus().equals(CampaignStatus.PENDING)) {
            throw new BusinessValidationException("Only PENDING campaign can be rejected.");
        }

        campaign.setStatus(CampaignStatus.REJECTED);

        return mapToDTO(campaignRepository.save(campaign));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"campaign","campaignSearch"}, allEntries = true)
    public CampaignResponseDTO updateCampaign(UUID campaignId, CampaignRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        // Only creator can update
        if (!campaign.getCreatedBy().getId().equals(user.getId())) {
            throw new UnauthorizedOperationException("You can update only your own campaign.");
        }

        // Target validation
        if (request.getTargetAmount() != null) {

            if (request.getTargetAmount().compareTo(campaign.getRaisedAmount()) < 0) {
                throw new BusinessValidationException(
                        "Target amount cannot be less than already raised amount."
                );
            }

            campaign.setTargetAmount(request.getTargetAmount());
        }

        // Allowed editable fields
        if (request.getTitle() != null) {
            campaign.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            campaign.setDescription(request.getDescription());
        }

        if (request.getBeneficiaryName() != null) {
            campaign.setBeneficiaryName(request.getBeneficiaryName());
        }

        if (request.getOrganizerName() != null) {
            campaign.setOrganizerName(request.getOrganizerName());
        }

        if (request.getLocation() != null) {
            campaign.setLocation(request.getLocation());
        }

        // If campaign already approved → reset moderation
        if (campaign.getStatus().equals(CampaignStatus.APPROVED)) {
            campaign.setStatus(CampaignStatus.PENDING);
        }

        Campaign updated = campaignRepository.save(campaign);

        return mapToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteCampaign(UUID campaignId, String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedOperationException("Only ADMIN can delete campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        if (campaign.getRaisedAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessValidationException("Campaign with donations cannot be deleted.");
        }

        campaignRepository.delete(campaign);
    }

    @Override
    @Cacheable(value = "campaignSearch", key = "#keyword + '-' + #category + '-' + #page + '-' + #size + '-' + #sortDir")
    public List<CampaignResponseDTO> searchCampaigns(
            String keyword,
            CampaignCategory category,
            int page,
            int size,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by("targetAmount").ascending()
                : Sort.by("targetAmount").descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Campaign> result =
                campaignRepository.findByStatusAndTitleContainingIgnoreCaseAndCategory(
                        CampaignStatus.APPROVED,
                        keyword == null ? "" : keyword,
                        category == null ? CampaignCategory.OTHER : category,
                        pageable
                );

        return result.map(this::mapToDTO).getContent();
    }


// --------

    @Override
    public List<CampaignResponseDTO> getApprovedCampaigns() {
        return campaignRepository.findByStatus(CampaignStatus.APPROVED)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "campaign", key = "#campaignId")
    public CampaignResponseDTO getApprovedCampaignById(UUID campaignId) {
        Campaign campaign = campaignRepository
                .findByIdAndStatus(campaignId, CampaignStatus.APPROVED)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));

        return mapToDTO(campaign);
    }

    @Override
    public List<CampaignResponseDTO> getApprovedCampaigns(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return campaignRepository
                .findByStatus(CampaignStatus.APPROVED, pageable)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


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