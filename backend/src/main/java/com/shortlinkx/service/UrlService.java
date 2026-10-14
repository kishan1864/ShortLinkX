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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UrlService {

    private static final Logger log =
            LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    private final UrlCacheService urlCacheService;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlService(
            UrlRepository urlRepository,
            UserRepository userRepository,
            ShortCodeGenerator shortCodeGenerator,
            UrlCacheService urlCacheService
    ) {
        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.urlCacheService = urlCacheService;
    }

    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request,
            String userEmail
    ) {

        User user = userRepository.findByEmail(
                userEmail.trim().toLowerCase()
        ).orElseThrow(() ->
                new UserNotFoundException(
                        "User not found with email: " + userEmail
                )
        );

        String originalUrl =
                request.getOriginalUrl().trim();

        String shortCode =
                shortCodeGenerator.generateUniqueCode();

        Url url = new Url(
                originalUrl,
                shortCode,
                user
        );

        urlRepository.save(url);

        String shortUrl =
                baseUrl + "/" + shortCode;

        return new ShortUrlResponse(
                originalUrl,
                shortUrl,
                shortCode
        );
    }

    public List<UrlHistoryResponse> getUserHistory(
            String userEmail
    ) {

        User user = userRepository.findByEmail(
                userEmail.trim().toLowerCase()
        ).orElseThrow(() ->
                new UserNotFoundException(
                        "User not found with email: " + userEmail
                )
        );

        List<Url> urls =
                urlRepository.findAllByUserOrderByCreatedAtDesc(user);

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

    public void deleteUrl(
            Long urlId,
            String userEmail
    ) {

        User user = userRepository.findByEmail(
                userEmail.trim().toLowerCase()
        ).orElseThrow(() ->
                new UserNotFoundException(
                        "User not found with email: " + userEmail
                )
        );

        Url url = urlRepository.findByIdAndUser(
                urlId,
                user
        ).orElseThrow(() ->
                new RuntimeException(
                        "URL not found or you do not have permission to delete it"
                )
        );

        urlRepository.delete(url);

        urlCacheService.evict(
                url.getShortCode()
        );
    }

    public String getOriginalUrl(String shortCode) {



        // 1. Check Redis first
        String cachedUrl =
                urlCacheService.getOriginalUrl(shortCode);

        if (cachedUrl != null) {

            log.info(
                    "Redis cache HIT for shortCode={}",
                    shortCode
            );

            return cachedUrl;
        }

        log.info(
                "Redis cache MISS for shortCode={}",
                shortCode
        );

        // 2. Redis miss -> query PostgreSQL
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Short URL not found"
                        )
                );

        String originalUrl =
                url.getOriginalUrl();

        // 3. Store the result in Redis
        urlCacheService.cacheOriginalUrl(
                shortCode,
                originalUrl
        );

        log.info(
                "Redis cache SET for shortCode={}",
                shortCode
        );

        // 4. Return original URL
        return originalUrl;
    }
}