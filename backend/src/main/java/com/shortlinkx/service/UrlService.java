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