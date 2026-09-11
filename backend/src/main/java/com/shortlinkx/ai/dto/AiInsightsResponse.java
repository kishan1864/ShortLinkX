package com.shortlinkx.ai.dto;

public class AiInsightsResponse {
    private AnalyticsSummary analytics;
    private String insights;

    public AiInsightsResponse() {}

    public AiInsightsResponse(AnalyticsSummary analytics, String insights) {
        this.analytics = analytics;
        this.insights = insights;
    }

    public AnalyticsSummary getAnalytics() { return analytics; }
    public void setAnalytics(AnalyticsSummary analytics) { this.analytics = analytics; }

    public String getInsights() { return insights; }
    public void setInsights(String insights) { this.insights = insights; }
}
