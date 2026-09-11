package com.shortlinkx.ai.service;

import com.shortlinkx.ai.dto.AnalyticsSummary;
import org.springframework.stereotype.Service;

@Service
public class DemoAnalyticsService {

    /*
     * Demo/seeded analytics for the AI MVP.
     * This data is stored in memory and resets when the backend restarts.
     */
    private AnalyticsSummary analytics = new AnalyticsSummary(
            12842,
            8219,
            61.0,
            32.0,
            7.0,
            "India",
            "Crome",
            18.2
    );

    public AnalyticsSummary getAnalytics() {
        return analytics;
    }

    public synchronized AnalyticsSummary updateAnalytics(AnalyticsSummary request) {
        validate(request);
        this.analytics = request;
        return this.analytics;
    }

    private void validate(AnalyticsSummary data) {
        if (data == null) {
            throw new IllegalArgumentException("Analytics data cannot be null");
        }
        if (data.getTotalClicks() < 0) {
            throw new IllegalArgumentException("totalClicks cannot be negative");
        }
        if (data.getUniqueVisitors() < 0) {
            throw new IllegalArgumentException("uniqueVisitors cannot be negative");
        }
        if (data.getMobilePercentage() < 0 || data.getMobilePercentage() > 100) {
            throw new IllegalArgumentException("mobilePercentage must be between 0 and 100");
        }
        if (data.getDesktopPercentage() < 0 || data.getDesktopPercentage() > 100) {
            throw new IllegalArgumentException("desktopPercentage must be between 0 and 100");
        }
        if (data.getTabletPercentage() < 0 || data.getTabletPercentage() > 100) {
            throw new IllegalArgumentException("tabletPercentage must be between 0 and 100");
        }

        double total = data.getMobilePercentage()
                + data.getDesktopPercentage()
                + data.getTabletPercentage();

        if (Math.abs(total - 100.0) > 0.01) {
            throw new IllegalArgumentException(
                    "mobilePercentage + desktopPercentage + tabletPercentage must equal 100"
            );
        }

        if (data.getTopCountry() == null || data.getTopCountry().isBlank()) {
            throw new IllegalArgumentException("topCountry cannot be empty");
        }

        if (data.getTopBrowser() == null || data.getTopBrowser().isBlank()) {
            throw new IllegalArgumentException("topBrowser cannot be empty");
        }
    }
}
