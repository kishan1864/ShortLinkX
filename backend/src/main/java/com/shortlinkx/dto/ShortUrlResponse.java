package com.shortlinkx.dto;

public class ShortUrlResponse {

    private String originalUrl;
    private String shortUrl;
    private String shortCode;

    public ShortUrlResponse() {
    }

    public ShortUrlResponse(
            String originalUrl,
            String shortUrl,
            String shortCode
    ) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }
}