package com.crowdfund.backend.document.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.crowdfund.backend.campaign.domain.Campaign;
import com.crowdfund.backend.campaign.repository.CampaignRepository;
import com.crowdfund.backend.document.domain.CampaignDocument;
import com.crowdfund.backend.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignDocumentServiceImpl implements CampaignDocumentService {

    private final CampaignRepository campaignRepository;
    private final DocumentRepository documentRepository;
    private final Cloudinary cloudinary;

    @Override
    @Transactional
    public CampaignDocument uploadDocument(UUID campaignId, MultipartFile file) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        try {

            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            String fileUrl = uploadResult.get("secure_url").toString();

            CampaignDocument document = CampaignDocument.builder()
                    .id(UUID.randomUUID())
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileUrl(fileUrl)
                    .uploadedAt(LocalDateTime.now())
                    .campaign(campaign)
                    .build();

            return documentRepository.save(document);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cloudinary upload failed", e);
        }
    }

    @Override
    public List<CampaignDocument> getCampaignDocuments(UUID campaignId) {
        return documentRepository.findByCampaignId(campaignId);
    }
}