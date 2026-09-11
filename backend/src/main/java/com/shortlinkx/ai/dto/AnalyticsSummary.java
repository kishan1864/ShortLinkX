package com.shortlinkx.ai.dto;

public class AnalyticsSummary {
    private long totalClicks;
    private long uniqueVisitors;
    private double mobilePercentage;
    private double desktopPercentage;
    private double tabletPercentage;
    private String topCountry;
    private String topBrowser;
    private double growthPercentage;

    public AnalyticsSummary() {}

    public AnalyticsSummary(long totalClicks, long uniqueVisitors,
                            double mobilePercentage, double desktopPercentage,
                            double tabletPercentage, String topCountry,
                            String topBrowser, double growthPercentage) {
        this.totalClicks = totalClicks;
        this.uniqueVisitors = uniqueVisitors;
        this.mobilePercentage = mobilePercentage;
        this.desktopPercentage = desktopPercentage;
        this.tabletPercentage = tabletPercentage;
        this.topCountry = topCountry;
        this.topBrowser = topBrowser;
        this.growthPercentage = growthPercentage;
    }

    public long getTotalClicks() { return totalClicks; }
    public void setTotalClicks(long totalClicks) { this.totalClicks = totalClicks; }

    public long getUniqueVisitors() { return uniqueVisitors; }
    public void setUniqueVisitors(long uniqueVisitors) { this.uniqueVisitors = uniqueVisitors; }

    public double getMobilePercentage() { return mobilePercentage; }
    public void setMobilePercentage(double mobilePercentage) { this.mobilePercentage = mobilePercentage; }

    public double getDesktopPercentage() { return desktopPercentage; }
    public void setDesktopPercentage(double desktopPercentage) { this.desktopPercentage = desktopPercentage; }

    public double getTabletPercentage() { return tabletPercentage; }
    public void setTabletPercentage(double tabletPercentage) { this.tabletPercentage = tabletPercentage; }

    public String getTopCountry() { return topCountry; }
    public void setTopCountry(String topCountry) { this.topCountry = topCountry; }

    public String getTopBrowser() { return topBrowser; }
    public void setTopBrowser(String topBrowser) { this.topBrowser = topBrowser; }

    public double getGrowthPercentage() { return growthPercentage; }
    public void setGrowthPercentage(double growthPercentage) { this.growthPercentage = growthPercentage; }
}
