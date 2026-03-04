package com.crowdfund.backend.payment.service;

public interface PaymentWebhookService {

    void processWebhook(String payload, String signature);

}