package com.crowdfund.backend.campaign.repository;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    List<Campaign> findByStatus(CampaignStatus status);
    Optional<Campaign> findByIdAndStatus(UUID id, CampaignStatus status);
    Page<Campaign> findByStatus(CampaignStatus status, Pageable pageable);
}




/*
-------------------------------------------------------
SUMMARY:
Handles DB operations for Campaign.
Supports filtering by approval status.
-------------------------------------------------------
*/