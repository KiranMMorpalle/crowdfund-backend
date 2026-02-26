package com.crowdfund.backend.campaign.service;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignStatus;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.common.exception.BusinessValidationException;
import com.crowdfund.backend.common.exception.ResourceNotFoundException;
import com.crowdfund.backend.common.exception.UnauthorizedOperationException;
import com.crowdfund.backend.user.domain.Role;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;


    public CampaignServiceImpl(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    // 1. Create Campaign
    @Override
    @Transactional
    public Campaign createCampaign(
            String title,
            String description,
            BigDecimal targetAmount,
            UUID creatorId) {
        if(title == null || title.isBlank()){
            throw new BusinessValidationException("Title cannot be empty.");
        }

        if(targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessValidationException("Target amount must be greater than zero.");
        }

        User creator = userRepository.findById(creatorId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Campaign campaign = new Campaign();
        campaign.setTitle(title);
        campaign.setDescription(description);
        campaign.setTargetAmount(targetAmount);
        campaign.setCreatedBy(creator);
        campaign.setStatus(CampaignStatus.PENDING);

        return campaignRepository.save(campaign);
    }



    // 2. Approve Campaign
    @Override
    @Transactional
    public Campaign approveCampaign(UUID campaignId, UUID adminId){
        User admin = userRepository.findById(adminId)
                .orElseThrow(()-> new ResourceNotFoundException("Admin not found."));

        if(!admin.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("Only ADMIN can approve campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(()-> new ResourceNotFoundException("Campaign not found."));

        if(!campaign.getStatus().equals(CampaignStatus.PENDING)){
            throw new BusinessValidationException("Only PENDING campaign can be approved.");
        }

        campaign.setStatus(CampaignStatus.APPROVED);

        return campaignRepository.save(campaign);
    }



// 3. Reject Campaign
@Override
@Transactional
public Campaign regectCampaign(UUID campaignId, UUID adminId){

        User admin = userRepository.findById(adminId)
                .orElseThrow(()-> new ResourceNotFoundException("Admin not found."));

        if(!admin.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("Only ADMIN can reject campaign.");
        }

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(()-> new ResourceNotFoundException("Campaign not found."));

        if(!campaign.getStatus().equals(CampaignStatus.PENDING)){
            throw new BusinessValidationException("Only PENDING campaign can be reject.");
        }

        campaign.setStatus(CampaignStatus.REJECTED);

        return campaignRepository.save(campaign);
}





    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public List<Campaign> getApprovedCampaigns(){
        return campaignRepository.findByStatus(CampaignStatus.APPROVED);
    }
}










/*
Optional<User> optionalUser = userRepository.findById(creatorId);

if (optionalUser.isEmpty()) {
        throw new ResourceNotFoundException("User not found");
}
User creator = optionalUser.get();

*/