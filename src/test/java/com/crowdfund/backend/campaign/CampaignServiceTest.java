package com.crowdfund.backend.campaign;

import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.domain.CampaignCategory;
import com.crowdfund.backend.campaign.domain.CampaignStatus;
import com.crowdfund.backend.campaign.dto.CampaignRequest;
import com.crowdfund.backend.campaign.dto.CampaignResponseDTO;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.campaign.service.CampaignServiceImpl;
import com.crowdfund.backend.common.exception.BusinessValidationException;
import com.crowdfund.backend.common.exception.ResourceNotFoundException;
import com.crowdfund.backend.common.exception.UnauthorizedOperationException;
import com.crowdfund.backend.user.domain.Role;
import com.crowdfund.backend.user.domain.User;
import com.crowdfund.backend.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private User user;
    private User admin;
    private Campaign campaign;
    private UUID campaignId;

    @BeforeEach
    void setup() {
        campaignId = UUID.randomUUID();

        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@test.com");
        user.setRole(Role.USER);

        admin = new User();
        admin.setId(UUID.randomUUID());
        admin.setEmail("admin@test.com");
        admin.setRole(Role.ADMIN);

        campaign = new Campaign();
        campaign.setId(campaignId);
        campaign.setTitle("Test Campaign");
        campaign.setTargetAmount(BigDecimal.valueOf(1000));
        campaign.setRaisedAmount(BigDecimal.ZERO);
        campaign.setStatus(CampaignStatus.PENDING);
        campaign.setCreatedBy(user);
    }

    // =========================
    // CREATE CAMPAIGN
    // =========================

    @Test
    void createCampaign_success() {
        CampaignRequest request = new CampaignRequest();
        request.setTitle("Test");
        request.setDescription("Desc");
        request.setTargetAmount(BigDecimal.valueOf(1000));
        request.setCategory(CampaignCategory.MEDICAL);
        request.setBeneficiaryName("Ben");
        request.setOrganizerName("Org");
        request.setLocation("Pune");

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(campaignRepository.save(any(Campaign.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CampaignResponseDTO response =
                campaignService.createCampaign(request, "user@test.com");

        assertNotNull(response);
        assertEquals("Test", response.getTitle());
        verify(campaignRepository).save(any(Campaign.class));
    }

    @Test
    void createCampaign_userNotFound() {
        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.empty());

        CampaignRequest request = new CampaignRequest();

        assertThrows(ResourceNotFoundException.class,
                () -> campaignService.createCampaign(request, "user@test.com"));
    }

    @Test
    void createCampaign_invalidTargetAmount() {
        CampaignRequest request = new CampaignRequest();
        request.setTargetAmount(BigDecimal.ZERO);

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        assertThrows(BusinessValidationException.class,
                () -> campaignService.createCampaign(request, "user@test.com"));
    }

    // =========================
    // APPROVE CAMPAIGN
    // =========================

    @Test
    void approveCampaign_success() {
        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(admin));

        when(campaignRepository.findById(campaignId))
                .thenReturn(Optional.of(campaign));

        when(campaignRepository.save(any()))
                .thenReturn(campaign);

        CampaignResponseDTO response =
                campaignService.approveCampaign(campaignId, "admin@test.com");

        assertEquals("APPROVED", response.getStatus());
    }

    @Test
    void approveCampaign_notAdmin() {
        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        assertThrows(UnauthorizedOperationException.class,
                () -> campaignService.approveCampaign(campaignId, "user@test.com"));
    }

    @Test
    void approveCampaign_campaignNotFound() {
        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(admin));

        when(campaignRepository.findById(campaignId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> campaignService.approveCampaign(campaignId, "admin@test.com"));
    }

    // =========================
    // UPDATE CAMPAIGN
    // =========================

    @Test
    void updateCampaign_success() {
        CampaignRequest request = new CampaignRequest();
        request.setTitle("Updated");

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(campaignRepository.findById(campaignId))
                .thenReturn(Optional.of(campaign));

        when(campaignRepository.save(any()))
                .thenReturn(campaign);

        CampaignResponseDTO response =
                campaignService.updateCampaign(campaignId, request, "user@test.com");

        assertNotNull(response);
    }

    @Test
    void updateCampaign_unauthorizedUser() {
        User anotherUser = new User();
        anotherUser.setId(UUID.randomUUID());

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(anotherUser));

        when(campaignRepository.findById(campaignId))
                .thenReturn(Optional.of(campaign));

        assertThrows(UnauthorizedOperationException.class,
                () -> campaignService.updateCampaign(campaignId, new CampaignRequest(), "user@test.com"));
    }

    // =========================
    // DELETE CAMPAIGN
    // =========================

    @Test
    void deleteCampaign_success() {
        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(admin));

        when(campaignRepository.findById(campaignId))
                .thenReturn(Optional.of(campaign));

        assertDoesNotThrow(() ->
                campaignService.deleteCampaign(campaignId, "admin@test.com"));

        verify(campaignRepository).delete(campaign);
    }

    @Test
    void deleteCampaign_withDonations_shouldFail() {
        campaign.setRaisedAmount(BigDecimal.valueOf(100));

        when(userRepository.findByEmail("admin@test.com"))
                .thenReturn(Optional.of(admin));

        when(campaignRepository.findById(campaignId))
                .thenReturn(Optional.of(campaign));

        assertThrows(BusinessValidationException.class,
                () -> campaignService.deleteCampaign(campaignId, "admin@test.com"));
    }

    // =========================
    // GET BY ID
    // =========================

    @Test
    void getApprovedCampaignById_success() {
        campaign.setStatus(CampaignStatus.APPROVED);

        when(campaignRepository.findByIdAndStatus(campaignId, CampaignStatus.APPROVED))
                .thenReturn(Optional.of(campaign));

        CampaignResponseDTO response =
                campaignService.getApprovedCampaignById(campaignId);

        assertNotNull(response);
    }

    @Test
    void getApprovedCampaignById_notFound() {
        when(campaignRepository.findByIdAndStatus(campaignId, CampaignStatus.APPROVED))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> campaignService.getApprovedCampaignById(campaignId));
    }
}
