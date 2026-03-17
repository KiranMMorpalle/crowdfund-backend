package com.crowdfund.backend.payment;

import com.crowdfund.backend.donation.domain.Donation;
import com.crowdfund.backend.donation.domain.DonationStatus;
import com.crowdfund.backend.donation.repository.DonationRepository;
import com.crowdfund.backend.payment.domain.Payment;
import com.crowdfund.backend.payment.domain.PaymentStatus;
import com.crowdfund.backend.payment.dto.PaymentVerificationRequest;
import com.crowdfund.backend.payment.repository.PaymentRepository;
import com.crowdfund.backend.payment.service.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void verifyPayment_success() {
        UUID donationId = UUID.randomUUID();

        // ✅ Create object normally
        Donation donation = new Donation();

        // ✅ Set private fields using reflection (SAFE for tests)
        ReflectionTestUtils.setField(donation, "id", donationId);
        ReflectionTestUtils.setField(donation, "amount", BigDecimal.valueOf(500));
        ReflectionTestUtils.setField(donation, "status", DonationStatus.PENDING);

        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .donation(donation)
                .amount(BigDecimal.valueOf(500))
                .provider("RAZORPAY")
                .status(PaymentStatus.CREATED)
                .build();

        PaymentVerificationRequest request = new PaymentVerificationRequest();
        request.setDonationId(donationId);
        request.setRazorpayPaymentId("pay_123");

        when(donationRepository.findById(donationId)).thenReturn(Optional.of(donation));
        when(paymentRepository.findByDonation(donation)).thenReturn(Optional.of(payment));

        Object result = paymentService.verifyPayment(request);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals(DonationStatus.SUCCESS, donation.getStatus());
        assertEquals("pay_123", payment.getProviderPaymentId());

        verify(paymentRepository, times(1)).save(payment);
        verify(donationRepository, times(1)).save(donation);
    }

    @Test
    void verifyPayment_donationNotFound() {
        UUID donationId = UUID.randomUUID();

        PaymentVerificationRequest request = new PaymentVerificationRequest();
        request.setDonationId(donationId);

        when(donationRepository.findById(donationId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.verifyPayment(request));

        assertEquals("Donation not found", ex.getMessage());
    }

    @Test
    void verifyPayment_paymentNotFound() {
        UUID donationId = UUID.randomUUID();

        Donation donation = new Donation();
        ReflectionTestUtils.setField(donation, "id", donationId);
        ReflectionTestUtils.setField(donation, "amount", BigDecimal.valueOf(500));
        ReflectionTestUtils.setField(donation, "status", DonationStatus.PENDING);

        PaymentVerificationRequest request = new PaymentVerificationRequest();
        request.setDonationId(donationId);

        when(donationRepository.findById(donationId)).thenReturn(Optional.of(donation));
        when(paymentRepository.findByDonation(donation)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.verifyPayment(request));

        assertEquals("Payment not found", ex.getMessage());
    }
}