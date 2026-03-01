package com.crowdfund.backend.donation.repository;

import com.crowdfund.backend.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {

    List<Donation> findByCampaignId(UUID campaignId);

}
