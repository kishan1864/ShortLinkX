package com.shortlinkx.controller;

import com.shortlinkx.dto.CreateShortUrlRequest;
import com.shortlinkx.dto.ShortUrlResponse;
import com.shortlinkx.service.UrlService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request
    ) {

        ShortUrlResponse response =
                urlService.createShortUrl(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}