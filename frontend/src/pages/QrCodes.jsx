import { useEffect, useState } from "react";
import {
  Download,
  QrCode,
  Link2,
  Copy,
  Check
} from "lucide-react";
import { QRCodeCanvas } from "qrcode.react";

import Card from "../components/common/Card";
import Button from "../components/common/Button";
import api from "../services/api";
import "./QrCodes.css";

export default function QrCodes() {
  const [mode, setMode] = useState("history");

  const [urls, setUrls] = useState([]);
  const [selectedUrl, setSelectedUrl] = useState("");

  const [customUrl, setCustomUrl] = useState("");
  const [qrValue, setQrValue] = useState("");

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [copied, setCopied] = useState(false);

  /*
   * Load the logged-in user's ShortLinkX URLs.
   * These are already saved in PostgreSQL through the History feature.
   */
  useEffect(() => {
    const loadHistory = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await api.get("/api/urls/history");

        const history = response.data || [];

        setUrls(history);

        // Select the newest URL automatically
        if (history.length > 0) {
          setSelectedUrl(history[0].shortUrl);
        }
      } catch (err) {
        console.error("Failed to load URL history:", err);

        if (
          err.response?.status === 401 ||
          err.response?.status === 403
        ) {
          setError(
            "Your session has expired. Please login again."
          );
        } else {
          setError(
            "Unable to load your ShortLinkX URLs."
          );
        }
      } finally {
        setLoading(false);
      }
    };

    loadHistory();
  }, []);

  /*
   * Generate a QR from the selected ShortLinkX URL.
   */
  const generateHistoryQr = () => {
    if (!selectedUrl) {
      setError("Please select a ShortLinkX URL.");
      return;
    }

    setError("");
    setQrValue(selectedUrl);
    setCopied(false);
  };

  /*
   * Validate and generate a QR from any external URL.
   *
   * IMPORTANT:
   * This does NOT call the backend.
   * Therefore the URL is NOT saved to PostgreSQL
   * and does NOT appear in History.
   */
  const generateCustomQr = () => {
    const value = customUrl.trim();

    if (!value) {
      setError("Please enter a URL.");
      setQrValue("");
      return;
    }

    try {
      const parsedUrl = new URL(value);

      if (
        parsedUrl.protocol !== "http:" &&
        parsedUrl.protocol !== "https:"
      ) {
        setError(
          "Please enter a valid HTTP or HTTPS URL."
        );
        setQrValue("");
        return;
      }

      setError("");
      setQrValue(value);
      setCopied(false);
    } catch {
      setError(
        "Please enter a valid URL, for example https://google.com"
      );
      setQrValue("");
    }
  };

  /*
   * Switch between History mode and Any URL mode.
   */
  const handleModeChange = (newMode) => {
    setMode(newMode);
    setError("");
    setCopied(false);

    if (newMode === "history") {
      setQrValue("");
    } else {
      setQrValue("");
    }
  };

  /*
   * Copy whichever URL is currently being shown in the QR.
   */
  const copyUrl = async () => {
    if (!qrValue) {
      return;
    }

    try {
      await navigator.clipboard.writeText(qrValue);

      setCopied(true);

      setTimeout(() => {
        setCopied(false);
      }, 1500);
    } catch (err) {
      console.error("Failed to copy URL:", err);

      setError("Unable to copy the URL.");
    }
  };

  /*
   * Download the visible QR code as PNG.
   */
  const downloadQr = () => {
    const canvas = document.querySelector(
      "#qr canvas"
    );

    if (!canvas) {
      setError("Please generate a QR code first.");
      return;
    }

    const link = document.createElement("a");

    link.download = "shortlinkx-qr.png";

    link.href = canvas.toDataURL("image/png");

    link.click();
  };

  /*
   * Find the currently selected History record.
   * Useful for displaying original URL and created date.
   */
  const selectedHistoryUrl = urls.find(
    (url) => url.shortUrl === selectedUrl
  );

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

  return (
    <div className="page page-enter">

      <div className="page-header">
        <div>
          <h1 className="page-title">
            QR Codes
          </h1>

          <p className="page-subtitle">
            Generate a scannable QR code for your
            ShortLinkX URL or any link.
          </p>
        </div>
      </div>

      <div className="qr-layout">

        {/* LEFT SIDE */}
        <Card className="qr-form">

          <div className="qr-icon">
            <QrCode size={22} />
          </div>

          <h2>
            QR Generator
          </h2>

          <p>
            Generate a QR code from one of your
            saved links or any URL.
          </p>

          {/* MODE SELECTOR */}
          <div
            style={{
              display: "flex",
              gap: "10px",
              marginTop: "18px",
              marginBottom: "20px"
            }}
          >

            <Button
              onClick={() =>
                handleModeChange("history")
              }
              variant={
                mode === "history"
                  ? "primary"
                  : "secondary"
              }
            >
              My ShortLinkX URLs
            </Button>

            <Button
              onClick={() =>
                handleModeChange("custom")
              }
              variant={
                mode === "custom"
                  ? "primary"
                  : "secondary"
              }
            >
              Any URL
            </Button>

          </div>

          {/* HISTORY MODE */}
          {mode === "history" && (
            <>
              <label>
                Select ShortLinkX URL
              </label>

              {loading ? (
                <div className="qr-input">
                  <span>
                    Loading your URLs...
                  </span>
                </div>
              ) : error && urls.length === 0 ? (
                <div className="qr-input">
                  <span>
                    {error}
                  </span>
                </div>
              ) : urls.length === 0 ? (
                <div className="qr-input">
                  <span>
                    No ShortLinkX URLs found.
                  </span>
                </div>
              ) : (
                <>
                  <div className="qr-input">
                    <Link2 size={16} />

                    <select
                      value={selectedUrl}
                      onChange={(event) =>
                        setSelectedUrl(
                          event.target.value
                        )
                      }
                    >
                      {urls.map((url) => (
                        <option
                          key={url.id}
                          value={url.shortUrl}
                        >
                          {url.shortUrl}
                        </option>
                      ))}
                    </select>
                  </div>

                  <Button
                    onClick={generateHistoryQr}
                    icon={<QrCode size={16} />}
                  >
                    Generate QR
                  </Button>
                </>
              )}
            </>
          )}

          {/* ANY URL MODE */}
          {mode === "custom" && (
            <>
              <label>
                Enter Any URL
              </label>

              <div className="qr-input">
                <Link2 size={16} />

                <input
                  type="url"
                  value={customUrl}
                  onChange={(event) =>
                    setCustomUrl(
                      event.target.value
                    )
                  }
                  placeholder="https://example.com"
                />
              </div>

              <Button
                onClick={generateCustomQr}
                icon={<QrCode size={16} />}
              >
                Generate QR
              </Button>

              <p
                style={{
                  fontSize: "12px",
                  marginTop: "10px",
                  opacity: 0.7
                }}
              >
                This URL is only used to generate
                the QR code. It will not be saved
                to your ShortLinkX history.
              </p>
            </>
          )}

          {/* ERROR */}
          {error && (
            <div
              className="analytics-error"
              style={{ marginTop: "16px" }}
            >
              {error}
            </div>
          )}

          {/* CURRENT URL ACTIONS */}
          {qrValue && (
            <div
              style={{
                display: "flex",
                gap: "10px",
                marginTop: "18px"
              }}
            >

              <Button
                onClick={copyUrl}
                icon={
                  copied ? (
                    <Check size={16} />
                  ) : (
                    <Copy size={16} />
                  )
                }
              >
                {copied
                  ? "Copied"
                  : "Copy URL"}
              </Button>

              <Button
                onClick={downloadQr}
                icon={
                  <Download size={16} />
                }
              >
                Download PNG
              </Button>

            </div>
          )}

        </Card>

        {/* RIGHT SIDE */}
        <Card className="qr-preview">

          <div
            id="qr"
            className="qr-box"
          >
            {qrValue ? (
              <QRCodeCanvas
                value={qrValue}
                size={220}
                bgColor="#FFFFFF"
                fgColor="#172554"
                includeMargin
                level="H"
              />
            ) : (
              <QrCode
                size={64}
                strokeWidth={1.5}
              />
            )}
          </div>

          <strong>
            {qrValue ||
              "Generate a QR code to preview it"}
          </strong>

          {/* EXTRA INFO ONLY FOR SAVED URL */}
          {mode === "history" &&
            selectedHistoryUrl &&
            qrValue && (
              <div
                style={{
                  marginTop: "18px",
                  width: "100%"
                }}
              >

                <p>
                  <strong>
                    Original URL:
                  </strong>{" "}
                  {selectedHistoryUrl.originalUrl}
                </p>

                <p>
                  <strong>
                    Created:
                  </strong>{" "}
                  {formatCreatedDate(
                    selectedHistoryUrl.createdAt
                  )}
                </p>

              </div>
            )}

        </Card>

      </div>

    </div>
  );
}