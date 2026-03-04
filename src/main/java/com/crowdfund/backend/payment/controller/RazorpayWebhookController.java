package com.crowdfund.backend.payment.controller;

import com.crowdfund.backend.payment.service.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class RazorpayWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature
    ) {

        paymentWebhookService.processWebhook(payload, signature);

        return ResponseEntity.ok("Webhook processed");
    }
}