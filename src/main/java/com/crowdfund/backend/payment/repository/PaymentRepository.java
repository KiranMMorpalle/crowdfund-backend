package com.crowdfund.backend.payment.repository;

import com.crowdfund.backend.payment.domain.Payment;
import com.crowdfund.backend.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByDonation(Donation donation);

}