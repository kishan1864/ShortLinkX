package com.shortlinkx.controller;

import com.shortlinkx.dto.CreateShortUrlRequest;
import com.shortlinkx.dto.ShortUrlResponse;
import com.shortlinkx.dto.UrlHistoryResponse;
import com.shortlinkx.service.UrlService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request,
            Authentication authentication
    ) {

        String userEmail =
                authentication.getName();

        ShortUrlResponse response =
                urlService.createShortUrl(
                        request,
                        userEmail
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<UrlHistoryResponse>> getHistory(
            Authentication authentication
    ) {

        String userEmail =
                authentication.getName();

        List<UrlHistoryResponse> history =
                urlService.getUserHistory(
                        userEmail
                );

        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String userEmail =
                authentication.getName();

        urlService.deleteUrl(
                id,
                userEmail
        );

        return ResponseEntity.noContent().build();
    }
}