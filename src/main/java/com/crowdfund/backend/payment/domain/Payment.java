package com.crowdfund.backend.payment.domain;

import com.crowdfund.backend.donation.domain.Donation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    private BigDecimal amount;

    private String provider;

    private String providerPaymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;
}