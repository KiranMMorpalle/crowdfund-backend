package com.crowdfund.backend.payment.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreatePaymentRequest {

    private UUID donationId;

}