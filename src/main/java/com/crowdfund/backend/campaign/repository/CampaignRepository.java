package com.crowdfund.backend.campaign.repository;

import com.crowdfund.backend.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
}
