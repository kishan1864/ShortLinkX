import {
  MousePointerClick,
  Users,
  Globe2,
  Smartphone,
  MapPin
} from "lucide-react";

import Card from "../components/common/Card";
import StatCard from "../components/analytics/StatCard";
import AnalyticsChart from "../components/analytics/AnalyticsChart";

import "./Analytics.css";

export default function Analytics() {
  const devices = [
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
      </div>

      {/* Statistics */}
      <div className="stats-grid">

        <StatCard
          title="Total clicks"
          value="12,842"
          change="+18.2%"
          icon={MousePointerClick}
        />

        <StatCard
          title="Unique visitors"
          value="8,219"
          change="+12.7%"
          icon={Users}
        />

        <StatCard
          title="Top country"
          value="India"
          change="+6.1%"
          icon={Globe2}
        />

        <StatCard
          title="Mobile traffic"
          value="61%"
          change="+4.8%"
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

    </div>
  );
}