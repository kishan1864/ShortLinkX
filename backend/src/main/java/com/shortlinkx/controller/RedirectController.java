package com.shortlinkx.controller;

import com.shortlinkx.service.UrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginalUrl(
            @PathVariable String shortCode
    ) {

        String originalUrl =
                urlService.getOriginalUrl(shortCode);

        return new RedirectView(originalUrl);
    }
}