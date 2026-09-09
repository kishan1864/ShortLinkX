package com.shortlinkx.service;

import com.shortlinkx.dto.CreateShortUrlRequest;
import com.shortlinkx.dto.ShortUrlResponse;
import com.shortlinkx.entity.Url;
import com.shortlinkx.repository.UrlRepository;
import com.shortlinkx.util.ShortCodeGenerator;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    public UrlService(
            UrlRepository urlRepository,
            ShortCodeGenerator shortCodeGenerator
    ) {
        this.urlRepository = urlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request
    ) {

        // 1. Get the original URL
        String originalUrl = request.getOriginalUrl().trim();

        // 2. Generate a unique short code
        String shortCode =
                shortCodeGenerator.generateUniqueCode();

        // 3. Create URL entity
        Url url = new Url(
                originalUrl,
                shortCode
        );

        // 4. Save URL in PostgreSQL
        urlRepository.save(url);

        // 5. Build the short URL
        String shortUrl =
                "http://localhost:8080/" + shortCode;

        // 6. Return response
        return new ShortUrlResponse(
                originalUrl,
                shortUrl,
                shortCode
        );
    }

    public String getOriginalUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Short URL not found"
                        )
                );

        return url.getOriginalUrl();
    }
}