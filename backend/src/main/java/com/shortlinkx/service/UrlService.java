package com.shortlinkx.service;

import com.shortlinkx.dto.CreateShortUrlRequest;
import com.shortlinkx.dto.ShortUrlResponse;
import com.shortlinkx.dto.UrlHistoryResponse;
import com.shortlinkx.entity.Url;
import com.shortlinkx.exception.UserNotFoundException;
import com.shortlinkx.repository.UrlRepository;
import com.shortlinkx.user.User;
import com.shortlinkx.user.UserRepository;
import com.shortlinkx.util.ShortCodeGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlService(
            UrlRepository urlRepository,
            UserRepository userRepository,
            ShortCodeGenerator shortCodeGenerator
    ) {
        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request,
            String userEmail
    ) {

        // 1. Find the authenticated user
        User user = userRepository.findByEmail(
                userEmail.trim().toLowerCase()
        ).orElseThrow(() ->
                new UserNotFoundException(
                        "User not found with email: " + userEmail
                )
        );

        // 2. Get the original URL
        String originalUrl =
                request.getOriginalUrl().trim();

        // 3. Generate a unique short code
        String shortCode =
                shortCodeGenerator.generateUniqueCode();

        // 4. Create URL and attach the owner
        Url url = new Url(
                originalUrl,
                shortCode,
                user
        );

        // 5. Save URL in PostgreSQL
        urlRepository.save(url);

        // 6. Build the short URL
        String shortUrl =
                baseUrl + "/" + shortCode;

        // 7. Return response
        return new ShortUrlResponse(
                originalUrl,
                shortUrl,
                shortCode
        );
    }

    public List<UrlHistoryResponse> getUserHistory(
            String userEmail
    ) {

        // 1. Find authenticated user
        User user = userRepository.findByEmail(
                userEmail.trim().toLowerCase()
        ).orElseThrow(() ->
                new UserNotFoundException(
                        "User not found with email: " + userEmail
                )
        );

        // 2. Find only this user's URLs
        List<Url> urls =
                urlRepository.findAllByUserOrderByCreatedAtDesc(user);

        // 3. Convert entities to DTOs
        return urls.stream()
                .map(url -> new UrlHistoryResponse(
                        url.getId(),
                        url.getOriginalUrl(),
                        url.getShortCode(),
                        baseUrl + "/" + url.getShortCode(),
                        url.getCreatedAt()
                ))
                .toList();
    }

    public String getOriginalUrl(String shortCode) {

        Url url =
                urlRepository.findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Short URL not found"
                                )
                        );

        return url.getOriginalUrl();
    }
}