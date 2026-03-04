package com.crowdfund.backend.payment.service;

import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.domain.DonationStatus;
import com.crowdfund.backend.donation.repository.DonationRepository;
import com.crowdfund.backend.payment.domain.Payment;
import com.crowdfund.backend.payment.domain.PaymentStatus;
import com.crowdfund.backend.payment.repository.PaymentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private final PaymentRepository paymentRepository;
    private final DonationRepository donationRepository;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    @Override
    public void processWebhook(String payload, String signature) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            String event = root.get("event").asText();

            if ("payment.captured".equals(event)) {

                JsonNode paymentEntity = root.get("payload")
                        .get("payment")
                        .get("entity");

                String razorpayPaymentId = paymentEntity.get("id").asText();

                Payment payment = paymentRepository
                        .findAll()
                        .stream()
                        .filter(p -> razorpayPaymentId.equals(p.getProviderPaymentId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Payment not found"));

                payment.setStatus(PaymentStatus.SUCCESS);

                Donation donation = payment.getDonation();
                donation.setStatus(DonationStatus.SUCCESS);

                paymentRepository.save(payment);
                donationRepository.save(donation);
            }

        } catch (Exception e) {
            throw new RuntimeException("Webhook processing failed");
        }
    }
}