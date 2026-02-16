package com.crowdfund.backend.campaign.repository;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;



public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    List<Campaign> findByStatus(CampaignStatus status);
}




/*
-------------------------------------------------------
SUMMARY:
Handles DB operations for Campaign.
Supports filtering by approval status.
-------------------------------------------------------
*/