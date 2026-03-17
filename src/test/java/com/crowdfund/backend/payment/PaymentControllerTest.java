package com.crowdfund.backend.payment;

import com.crowdfund.backend.payment.controller.PaymentController;
import com.crowdfund.backend.payment.dto.PaymentVerificationRequest;
import com.crowdfund.backend.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = PaymentController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                com.crowdfund.backend.auth.security.JwtAuthenticationFilter.class
                        }
                )
        }
)
@AutoConfigureMockMvc(addFilters = false) // ✅ disables Spring Security filters
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_success() throws Exception {
        UUID donationId = UUID.randomUUID();

        when(paymentService.createOrder(donationId))
                .thenReturn("order_created");

        mockMvc.perform(post("/api/v1/payments/order/{donationId}", donationId))
                .andExpect(status().isOk());
    }

    @Test
    void verifyPayment_success() throws Exception {
        PaymentVerificationRequest request = new PaymentVerificationRequest();
        request.setDonationId(UUID.randomUUID());
        request.setRazorpayPaymentId("pay_123");

        when(paymentService.verifyPayment(request))
                .thenReturn("verified");

        mockMvc.perform(post("/api/v1/payments/verify")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}