package com.crowdfund.backend.document.controller;

import com.crowdfund.backend.document.domain.CampaignDocument;
import com.crowdfund.backend.document.service.CampaignDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignDocumentController {

    private final CampaignDocumentService documentService;

//    @PostMapping("/{campaignId}/documents")
//    public ResponseEntity<CampaignDocument> uploadDocument(
//            @PathVariable UUID campaignId,
//            @RequestParam("file") MultipartFile file) {
//
//        return ResponseEntity.ok(documentService.uploadDocument(campaignId, file));
//    }

    @PostMapping("/{campaignId}/documents")
    public ResponseEntity<?> uploadDocument(
            @PathVariable UUID campaignId,
            @RequestParam("file") MultipartFile file
    ) {
        CampaignDocument document = documentService.uploadDocument(campaignId, file);

        return ResponseEntity.ok(document);
    }

    @GetMapping("/{campaignId}/documents")
    public ResponseEntity<List<CampaignDocument>> getDocuments(
            @PathVariable UUID campaignId) {

        return ResponseEntity.ok(documentService.getCampaignDocuments(campaignId));
    }
}