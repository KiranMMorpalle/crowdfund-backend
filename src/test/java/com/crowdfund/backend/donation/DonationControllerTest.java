package com.crowdfund.backend.donation;

import com.crowdfund.backend.donation.controller.DonationController;
import com.crowdfund.backend.donation.dto.DonationRequestDTO;
import com.crowdfund.backend.donation.service.DonationService;
import com.crowdfund.backend.auth.security.JwtAuthenticationFilter;
import com.crowdfund.backend.auth.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DonationController.class)
@AutoConfigureMockMvc(addFilters = false)
class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonationService donationService;

    // 🔥 CRITICAL FIX (these were missing)
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void donate_success() throws Exception {

        UUID campaignId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        DonationRequestDTO request = new DonationRequestDTO();
        request.setAmount(BigDecimal.valueOf(1000));

        when(donationService.donate(any(), any(), any()))
                .thenReturn(null);

        mockMvc.perform(post("/api/v1/campaigns/" + campaignId + "/donate")
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void confirmDonation_success() throws Exception {

        UUID donationId = UUID.randomUUID();

        when(donationService.confirmDonation(any()))
                .thenReturn(null);

        mockMvc.perform(post("/api/v1/campaigns/donations/" + donationId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}