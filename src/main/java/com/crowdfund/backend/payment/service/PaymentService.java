package com.crowdfund.backend.payment.service;

import com.crowdfund.backend.payment.dto.PaymentVerificationRequest;

import java.util.UUID;

public interface PaymentService {

    Object createOrder(UUID donationId);

    Object verifyPayment(PaymentVerificationRequest request);
}









//package com.crowdfund.backend.payment.service;
//
//import com.crowdfund.backend.donation.domain.Donation;
//import com.crowdfund.backend.donation.repository.DonationRepository;
//import com.crowdfund.backend.payment.domain.Payment;
//import com.crowdfund.backend.payment.domain.PaymentStatus;
//import com.crowdfund.backend.payment.repository.PaymentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final DonationRepository donationRepository;
//
//    public Payment createPayment(UUID donationId) {
//
//        Donation donation = donationRepository.findById(donationId)
//                .orElseThrow(() -> new RuntimeException("Donation not found"));
//
//        Payment payment = Payment.builder()
//                .id(UUID.randomUUID())
//                .donation(donation)
//                .amount(donation.getAmount())
//                .provider("RAZORPAY")
//                .status(PaymentStatus.CREATED)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        return paymentRepository.save(payment);
//    }
//}