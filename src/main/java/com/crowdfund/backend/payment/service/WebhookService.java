package com.crowdfund.backend.payment.service;

import com.crowdfund.backend.payment.repository.PaymentRepository;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final PaymentRepository paymentRepository;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    public void processWebhook(String payload, String signature) {

        try {

            boolean isValid = Utils.verifyWebhookSignature(payload, signature, webhookSecret);

            if (!isValid) {
                throw new RuntimeException("Invalid webhook");
            }

        } catch (Exception e) {
            throw new RuntimeException("Webhook verification failed");
        }

    }
}