package com.crowdfund.backend.campaign.dto;

import java.util.UUID;

public class UserSummaryDTO {

    private UUID id;
    private String name;

    public UserSummaryDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
