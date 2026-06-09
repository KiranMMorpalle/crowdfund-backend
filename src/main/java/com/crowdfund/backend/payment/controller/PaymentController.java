package com.crowdfund.backend.payment.controller;

import com.crowdfund.backend.payment.dto.PaymentVerificationRequest;
import com.crowdfund.backend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/order/{donationId}")
    public ResponseEntity<?> createOrder(@PathVariable UUID donationId) {
        return ResponseEntity.ok(paymentService.createOrder(donationId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        return ResponseEntity.ok(paymentService.verifyPayment(request));
    }
}