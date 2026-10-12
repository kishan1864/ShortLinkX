package com.shortlinkx.dto;

import java.time.LocalDateTime;

public class UrlHistoryResponse {

    private Long id;
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    private LocalDateTime createdAt;

    public UrlHistoryResponse() {
    }

    public UrlHistoryResponse(
            Long id,
            String originalUrl,
            String shortCode,
            String shortUrl,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}