package com.shortlinkx.ai.controller;

import com.shortlinkx.ai.dto.AiInsightsResponse;
import com.shortlinkx.ai.dto.AnalyticsSummary;
import com.shortlinkx.ai.service.DemoAnalyticsService;
import com.shortlinkx.ai.service.GroqAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsAiController {

    private final DemoAnalyticsService analyticsService;
    private final GroqAiService groqAiService;

    public AnalyticsAiController(
            DemoAnalyticsService analyticsService,
            GroqAiService groqAiService
    ) {
        this.analyticsService = analyticsService;
        this.groqAiService = groqAiService;
    }

    @GetMapping("/demo")
    public ResponseEntity<AnalyticsSummary> getDemoAnalytics() {
        return ResponseEntity.ok(analyticsService.getAnalytics());
    }

    @PutMapping("/demo")
    public ResponseEntity<AnalyticsSummary> updateDemoAnalytics(
            @RequestBody AnalyticsSummary request
    ) {
        return ResponseEntity.ok(
                analyticsService.updateAnalytics(request)
        );
    }

    @GetMapping("/ai-insights")
    public ResponseEntity<AiInsightsResponse> getAiInsights() {
        String insights = groqAiService.generateAnalyticsInsights();

        return ResponseEntity.ok(
                new AiInsightsResponse(
                        analyticsService.getAnalytics(),
                        insights
                )
        );
    }
}
