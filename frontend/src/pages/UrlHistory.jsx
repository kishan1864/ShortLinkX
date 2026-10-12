import { useEffect, useMemo, useState } from "react";
import {
  Search,
  MoreHorizontal,
  Link2,
  Copy,
  Check,
  ExternalLink
} from "lucide-react";

import Card from "../components/common/Card";
import api from "../services/api";
import "./UrlHistory.css";

export default function UrlHistory() {
  const [urls, setUrls] = useState([]);
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [copiedId, setCopiedId] = useState(null);

  useEffect(() => {
    const loadHistory = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await api.get("/api/urls/history");

        setUrls(response.data);
      } catch (err) {
        console.error("Failed to load URL history:", err);

        if (err.response?.status === 401 || err.response?.status === 403) {
          setError("Your session has expired. Please login again.");
        } else {
          setError("Unable to load your URL history.");
        }
      } finally {
        setLoading(false);
      }
    };

    loadHistory();
  }, []);

  const filteredUrls = useMemo(() => {
    const query = search.trim().toLowerCase();

    if (!query) {
      return urls;
    }

    return urls.filter((url) =>
      url.shortCode.toLowerCase().includes(query) ||
      url.originalUrl.toLowerCase().includes(query)
    );
  }, [urls, search]);

  const formatCreatedDate = (value) => {
    if (!value) {
      return "-";
    }

    const date = new Date(value);

    if (Number.isNaN(date.getTime())) {
      return value;
    }

    return date.toLocaleDateString();
  };

  const copyShortUrl = async (url, id) => {
    try {
      await navigator.clipboard.writeText(url);

      setCopiedId(id);

      setTimeout(() => {
        setCopiedId(null);
      }, 1500);
    } catch (err) {
      console.error("Failed to copy URL:", err);
    }
  };

  if (loading) {
    return (
      <div className="page page-enter">
        <div className="page-header">
          <div>
            <h1 className="page-title">URL History</h1>
            <p className="page-subtitle">
              Manage and revisit every link you've created.
            </p>
          </div>
        </div>

        <Card className="history-card">
          <p>Loading your URL history...</p>
        </Card>
      </div>
    );
  }

  return (
    <div className="page page-enter">

      <div className="page-header">
        <div>
          <h1 className="page-title">URL History</h1>

          <p className="page-subtitle">
            Manage and revisit every link you've created.
          </p>
        </div>
      </div>

      <Card className="history-card">

        <div className="history-toolbar">

          <div className="history-search">
            <Search size={16} />

            <input
              value={search}
              onChange={(event) =>
                setSearch(event.target.value)
              }
              placeholder="Search by short code or URL"
            />
          </div>

          <select defaultValue="Active">
            <option value="Active">Active</option>
          </select>

        </div>

        {error && (
          <div className="analytics-error">
            {error}
          </div>
        )}

        {!error && filteredUrls.length === 0 && (
          <div className="history-empty">
            <Link2 size={24} />

            <h3>
              {search
                ? "No matching links"
                : "No URLs yet"}
            </h3>

            <p>
              {search
                ? "Try a different search."
                : "Create your first short URL and it will appear here."}
            </p>
          </div>
        )}

        {!error && filteredUrls.length > 0 && (
          <div className="table-wrap">
            <table>

              <thead>
                <tr>
                  <th>Short link</th>
                  <th>Destination</th>
                  <th>Created</th>
                  <th>Clicks</th>
                  <th>Status</th>
                  <th />
                </tr>
              </thead>

              <tbody>

                {filteredUrls.map((url) => (
                  <tr key={url.id}>

                    <td>
                      <div className="table-link">

                        <span>
                          <Link2 size={14} />
                        </span>

                        <strong>
                          {url.shortCode}
                        </strong>

                      </div>
                    </td>

                    <td className="destination">
                      {url.originalUrl}
                    </td>

                    <td>
                      {formatCreatedDate(url.createdAt)}
                    </td>

                    <td className="bold">
                      0
                    </td>

                    <td>
                      <span className="status active">
                        Active
                      </span>
                    </td>

                    <td>
                      <div
                        style={{
                          display: "flex",
                          gap: "6px"
                        }}
                      >

                        <button
                          className="more-btn"
                          title="Copy short URL"
                          onClick={() =>
                            copyShortUrl(
                              url.shortUrl,
                              url.id
                            )
                          }
                        >
                          {copiedId === url.id ? (
                            <Check size={17} />
                          ) : (
                            <Copy size={17} />
                          )}
                        </button>

                        <button
                          className="more-btn"
                          title="Open short URL"
                          onClick={() =>
                            window.open(
                              url.shortUrl,
                              "_blank",
                              "noopener,noreferrer"
                            )
                          }
                        >
                          <ExternalLink size={17} />
                        </button>

                        <button
                          className="more-btn"
                          title="More options"
                        >
                          <MoreHorizontal size={17} />
                        </button>

                      </div>
                    </td>

                  </tr>
                ))}

              </tbody>

            </table>
          </div>
        )}

      </Card>
    </div>
  );
}