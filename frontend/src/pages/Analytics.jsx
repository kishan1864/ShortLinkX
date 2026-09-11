import { useEffect, useState } from "react";

import {
  MousePointerClick,
  Users,
  Globe2,
  Smartphone,
  MapPin,
  Sparkles,
  Lightbulb,
  TrendingUp,
  AlertCircle,
  RefreshCw
} from "lucide-react";

import Card from "../components/common/Card";
import StatCard from "../components/analytics/StatCard";
import AnalyticsChart from "../components/analytics/AnalyticsChart";

import api from "../services/api";

import "./Analytics.css";

export default function Analytics() {
  const [analytics, setAnalytics] = useState(null);
  const [insights, setInsights] = useState("");
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [error, setError] = useState("");

  const devices = analytics
    ? [
        ["Mobile", `${analytics.mobilePercentage}%`],
        ["Desktop", `${analytics.desktopPercentage}%`],
        ["Tablet", `${analytics.tabletPercentage}%`]
      ]
    : [
        ["Mobile", "61%"],
        ["Desktop", "32%"],
        ["Tablet", "7%"]
      ];

  const locations = [
    ["India", "4,921"],
    ["United States", "2,110"],
    ["United Kingdom", "884"],
    ["Germany", "602"]
  ];

  const loadAiInsights = async (isRefresh = false) => {
    try {
      if (isRefresh) {
        setRefreshing(true);
      } else {
        setLoading(true);
      }

      setError("");

      const response = await api.get("/api/analytics/ai-insights");

      setAnalytics(response.data.analytics);
      setInsights(response.data.insights);
    } catch (err) {
      console.error("Failed to load AI insights:", err);

      if (err.response?.status === 401 || err.response?.status === 403) {
        setError("Your session has expired. Please log in again.");
      } else {
        setError(
          err.response?.data?.message ||
            "Unable to load AI insights. Please try again."
        );
      }
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => {
    loadAiInsights();
  }, []);

  const parseInsights = (text) => {
    if (!text) {
      return {
        observations: [],
        trends: [],
        recommendations: []
      };
    }

    const cleanText = text.replace(/\r/g, "");

    const observationsMatch = cleanText.match(
      /\*\*Key Observations\*\*\s*([\s\S]*?)(?=\*\*Trends\*\*|$)/i
    );

    const trendsMatch = cleanText.match(
      /\*\*Trends\*\*\s*([\s\S]*?)(?=\*\*Recommendations\*\*|$)/i
    );

    const recommendationsMatch = cleanText.match(
      /\*\*Recommendations\*\*\s*([\s\S]*)/i
    );

    const extractItems = (section = "") => {
      return section
        .split("\n")
        .map((line) => line.trim())
        .filter((line) => line.startsWith("-") || /^\d+\./.test(line))
        .map((line) => line.replace(/^[-]\s*/, "").replace(/^\d+\.\s*/, ""));
    };

    return {
      observations: extractItems(observationsMatch?.[1]),
      trends: extractItems(trendsMatch?.[1]),
      recommendations: extractItems(recommendationsMatch?.[1])
    };
  };

  const parsedInsights = parseInsights(insights);

  return (
    <div className="page page-enter">

      {/* Header */}
      <div className="page-header">
        <div>
          <h1 className="page-title">Analytics</h1>

          <p className="page-subtitle">
            Understand how people interact with your links.
          </p>
        </div>

        <button
          className="analytics-refresh-btn"
          onClick={() => loadAiInsights(true)}
          disabled={refreshing}
        >
          <RefreshCw
            size={16}
            className={refreshing ? "spin-icon" : ""}
          />

          {refreshing ? "Refreshing..." : "Refresh AI"}
        </button>
      </div>

      {/* Error */}
      {error && (
        <div className="analytics-error">
          <AlertCircle size={18} />
          <span>{error}</span>
        </div>
      )}

      {/* Statistics */}
      <div className="stats-grid">

        <StatCard
          title="Total clicks"
          value={analytics?.totalClicks?.toLocaleString() || "—"}
          change={
            analytics
              ? `+${analytics.growthPercentage}%`
              : "—"
          }
          icon={MousePointerClick}
        />

        <StatCard
          title="Unique visitors"
          value={analytics?.uniqueVisitors?.toLocaleString() || "—"}
          change="AI analyzed"
          icon={Users}
        />

        <StatCard
          title="Top country"
          value={analytics?.topCountry || "—"}
          change={analytics?.topBrowser || "—"}
          icon={Globe2}
        />

        <StatCard
          title="Mobile traffic"
          value={
            analytics
              ? `${analytics.mobilePercentage}%`
              : "—"
          }
          change={
            analytics
              ? `Growth +${analytics.growthPercentage}%`
              : "—"
          }
          icon={Smartphone}
        />

      </div>

      {/* Analytics section */}
      <div className="analytics-layout">

        <AnalyticsChart />

        <Card className="breakdown-card">

          <h3>Devices</h3>

          <p>Traffic by device</p>

          {devices.map(([name, percentage]) => (
            <div key={name}>

              <div className="device-row">
                <span>{name}</span>
                <strong>{percentage}</strong>
              </div>

              <div className="bar">
                <i
                  style={{
                    width: percentage
                  }}
                />
              </div>

            </div>
          ))}

          <div className="device-divider" />

          <h3>Top locations</h3>

          <p>Where your clicks come from</p>

          {locations.map(([country, clicks]) => (
            <div
              className="location-row"
              key={country}
            >
              <span>
                <MapPin size={14} />
                {country}
              </span>

              <strong>{clicks}</strong>
            </div>
          ))}

        </Card>

      </div>

      {/* AI Insights */}
      <div className="ai-insights-section">

        <div className="ai-insights-header">

          <div>
            <div className="ai-title-row">
              <Sparkles size={21} />
              <h2>AI Insights</h2>
            </div>

            <p>
              AI-powered analysis of your current link performance.
            </p>
          </div>

        </div>

        {loading ? (
          <Card className="ai-loading-card">
            <Sparkles size={22} />

            <div>
              <h3>Analyzing your analytics...</h3>
              <p>
                Groq AI is generating insights from your current metrics.
              </p>
            </div>
          </Card>
        ) : (
          <div className="ai-insights-grid">

            {/* Key Observations */}
            <Card className="ai-insight-card">

              <div className="ai-card-icon">
                <Lightbulb size={20} />
              </div>

              <h3>Key Observations</h3>

              {parsedInsights.observations.length > 0 ? (
                <ul>
                  {parsedInsights.observations.map((item, index) => (
                    <li key={index}>{item}</li>
                  ))}
                </ul>
              ) : (
                <p>No observations available.</p>
              )}

            </Card>

            {/* Trends */}
            <Card className="ai-insight-card">

              <div className="ai-card-icon">
                <TrendingUp size={20} />
              </div>

              <h3>Trends</h3>

              {parsedInsights.trends.length > 0 ? (
                <ul>
                  {parsedInsights.trends.map((item, index) => (
                    <li key={index}>{item}</li>
                  ))}
                </ul>
              ) : (
                <p>No trends available.</p>
              )}

            </Card>

            {/* Recommendations */}
            <Card className="ai-insight-card ai-recommendation-card">

              <div className="ai-card-icon">
                <Sparkles size={20} />
              </div>

              <h3>Recommendations</h3>

              {parsedInsights.recommendations.length > 0 ? (
                <ol>
                  {parsedInsights.recommendations.map((item, index) => (
                    <li key={index}>{item}</li>
                  ))}
                </ol>
              ) : (
                <p>No recommendations available.</p>
              )}

            </Card>

          </div>
        )}

      </div>

    </div>
  );
}