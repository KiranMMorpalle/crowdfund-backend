package com.crowdfund.backend.campaign;

import com.crowdfund.backend.auth.security.JwtService;
import com.crowdfund.backend.auth.service.CustomUserDetailsService;
import com.crowdfund.backend.campaign.controller.CampaignController;
import com.crowdfund.backend.campaign.domain.CampaignCategory;
import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.dto.UserSummaryDTO;
import com.crowdfund.backend.campaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = CampaignController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        })
class CampaignControllerTest {

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
                "Test Campaign",
                "Description",
                BigDecimal.valueOf(1000),
                BigDecimal.ZERO,
                "APPROVED",
                LocalDateTime.now(),
                new UserSummaryDTO(UUID.randomUUID(), "Kiran")
        );
    }

    // =========================
    // CREATE CAMPAIGN
    // =========================
    @Test
    void createCampaign_success() throws Exception {

        CampaignRequest request = new CampaignRequest();
        request.setTitle("Test");
        request.setDescription("Desc");
        request.setTargetAmount(BigDecimal.valueOf(1000));
        request.setCategory(CampaignCategory.MEDICAL);
        request.setBeneficiaryName("Ben");
        request.setOrganizerName("Org");
        request.setLocation("Pune");

        when(campaignService.createCampaign(any(), anyString()))
                .thenReturn(mockResponse());

        mockMvc.perform(post("/api/v1/campaigns")
                        .with(user("user@test.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    // =========================
    // UPDATE CAMPAIGN
    // =========================
    @Test
    void updateCampaign_success() throws Exception {

        UUID id = UUID.randomUUID();

        CampaignRequest request = new CampaignRequest();
        request.setTitle("Updated");

        when(campaignService.updateCampaign(eq(id), any(), anyString()))
                .thenReturn(mockResponse());

        mockMvc.perform(put("/api/v1/campaigns/{id}", id)
                        .with(user("user@test.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // =========================
    // DELETE CAMPAIGN
    // =========================
    @Test
    void deleteCampaign_success() throws Exception {

        UUID id = UUID.randomUUID();

        doNothing().when(campaignService).deleteCampaign(eq(id), anyString());

        mockMvc.perform(delete("/api/v1/campaigns/{id}", id)
                        .with(user("admin@test.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // =========================
    // APPROVE CAMPAIGN
    // =========================
    @Test
    void approveCampaign_success() throws Exception {

        UUID id = UUID.randomUUID();

        when(campaignService.approveCampaign(eq(id), anyString()))
                .thenReturn(mockResponse());

        mockMvc.perform(patch("/api/v1/campaigns/{id}/approve", id)
                        .with(user("admin@test.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // =========================
    // GET BY ID
    // =========================
    @Test
    void getCampaignById_success() throws Exception {

        UUID id = UUID.randomUUID();

        when(campaignService.getApprovedCampaignById(eq(id)))
                .thenReturn(mockResponse());

        mockMvc.perform(get("/api/v1/campaigns/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    // =========================
    // SEARCH
    // =========================
    @Test
    void searchCampaigns_success() throws Exception {

        when(campaignService.searchCampaigns(any(), any(), anyInt(), anyInt(), any()))
                .thenReturn(List.of(mockResponse()));

        mockMvc.perform(get("/api/v1/campaigns")
                        .param("keyword", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}