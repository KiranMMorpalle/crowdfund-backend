package com.crowdfund.backend.payment.service;

import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.domain.DonationStatus;
import com.crowdfund.backend.donation.repository.DonationRepository;
import com.crowdfund.backend.payment.domain.Payment;
import com.crowdfund.backend.payment.domain.PaymentStatus;
import com.crowdfund.backend.payment.dto.PaymentVerificationRequest;
import com.crowdfund.backend.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final DonationRepository donationRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Object createOrder(UUID donationId) {
        return null;
    }

    @Override
    public Object verifyPayment(PaymentVerificationRequest request) {

        Donation donation = donationRepository.findById(request.getDonationId())
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        Payment payment = paymentRepository.findByDonation(donation)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setProviderPaymentId(request.getRazorpayPaymentId());
        payment.setStatus(PaymentStatus.SUCCESS);

        donation.setStatus(DonationStatus.SUCCESS);

        paymentRepository.save(payment);
        donationRepository.save(donation);

        return payment;
    }
}