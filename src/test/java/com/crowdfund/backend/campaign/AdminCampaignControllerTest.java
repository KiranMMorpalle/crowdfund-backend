package com.crowdfund.backend.campaign;

import com.crowdfund.backend.auth.security.JwtService;
import com.crowdfund.backend.auth.service.CustomUserDetailsService;
import com.crowdfund.backend.campaign.controller.AdminCampaignController;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.dto.UserSummaryDTO;
import com.crowdfund.backend.campaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminCampaignController.class)
class AdminCampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // MOCK DATA
    // =========================
    private CampaignResponseDTO mockResponse() {
        return new CampaignResponseDTO(
                UUID.randomUUID(),
                "Admin Campaign",
                "Desc",
                BigDecimal.valueOf(5000),
                BigDecimal.ZERO,
                "PENDING",
                LocalDateTime.now(),
                new UserSummaryDTO(UUID.randomUUID(), "Kiran")
        );
    }

    // =========================
    // APPROVE CAMPAIGN
    // =========================
    @Test
    @WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
    void approveCampaign_success() throws Exception {

        UUID id = UUID.randomUUID();

        when(campaignService.approveCampaign(eq(id), anyString()))
                .thenReturn(mockResponse());

        mockMvc.perform(put("/api/v1/admin/campaigns/{id}/approve", id)
                        .with(csrf()))   // ✅ FIX HERE
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // =========================
    // REJECT CAMPAIGN
    // =========================
    @Test
    @WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
    void rejectCampaign_success() throws Exception {

        UUID id = UUID.randomUUID();

        when(campaignService.rejectCampaign(eq(id), anyString()))
                .thenReturn(mockResponse());

        mockMvc.perform(put("/api/v1/admin/campaigns/{id}/reject", id)
                        .with(csrf()))   // ✅ FIX HERE
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}