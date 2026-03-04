package com.crowdfund.backend.payment.service;

import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.repository.DonationRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    private final DonationRepository donationRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public String createOrder(UUID donationId) throws Exception {

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();

        orderRequest.put("amount", donation.getAmount().multiply(new java.math.BigDecimal(100)));
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", donationId.toString());

        Order order = razorpay.orders.create(orderRequest);

        return order.toString();
    }
}