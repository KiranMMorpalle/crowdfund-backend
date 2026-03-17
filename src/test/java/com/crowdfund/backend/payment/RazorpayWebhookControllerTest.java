package com.crowdfund.backend.payment;

import com.crowdfund.backend.payment.controller.RazorpayWebhookController;
import com.crowdfund.backend.payment.service.PaymentWebhookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = RazorpayWebhookController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                com.crowdfund.backend.auth.security.JwtAuthenticationFilter.class
                        }
                )
        }
)
@AutoConfigureMockMvc(addFilters = false) // ✅ disables security completely
class RazorpayWebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentWebhookService paymentWebhookService;

    @Test
    void webhook_success() throws Exception {

        String payload = "{\"event\":\"payment.captured\"}";
        String signature = "test_signature";

        doNothing().when(paymentWebhookService)
                .processWebhook(payload, signature);

        mockMvc.perform(post("/api/v1/payments/webhook")
                        .content(payload)
                        .header("X-Razorpay-Signature", signature)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}