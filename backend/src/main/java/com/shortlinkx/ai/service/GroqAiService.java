package com.shortlinkx.ai.service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;
import com.shortlinkx.ai.dto.AnalyticsSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class GroqAiService {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final DemoAnalyticsService analyticsService;
    private final String apiKey;
    private final String model;
    private final String endpoint;

    public GroqAiService(
            ObjectMapper objectMapper,
            DemoAnalyticsService analyticsService,
            @Value("${app.groq.api-key}") String apiKey,
            @Value("${app.groq.model:openai/gpt-oss-20b}") String model,
            @Value("${app.groq.endpoint:https://api.groq.com/openai/v1/chat/completions}") String endpoint
    ) {
        this.objectMapper = objectMapper;
        this.analyticsService = analyticsService;
        this.apiKey = apiKey;
        this.model = model;
        this.endpoint = endpoint;

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
    }

    public String generateAnalyticsInsights() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Groq API key is not configured. Set GROQ_API_KEY."
            );
        }

        AnalyticsSummary metrics = analyticsService.getAnalytics();

        try {
            ObjectNode body = objectMapper.createObjectNode();
            body.put("model", model);
            body.put("temperature", 0.2);

            ArrayNode messages = body.putArray("messages");

            ObjectNode systemMessage = messages.addObject();
            systemMessage.put("role", "system");
            systemMessage.put(
                    "content",
                    "You are the analytics assistant for ShortLinkX. "
                    + "Analyze only the metrics provided. Do not invent numbers. "
                    + "Return three concise sections: Key observations, Trends, Recommendations."
            );

            ObjectNode userMessage = messages.addObject();
            userMessage.put("role", "user");
            userMessage.put("content", buildPrompt(metrics));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(60))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(body)
                    ))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException(
                        "Groq API returned HTTP "
                                + response.statusCode()
                                + ": "
                                + response.body()
                );
            }

            JsonNode json = objectMapper.readTree(response.body());

            JsonNode content = json.path("choices")
                    .path(0)
                    .path("message")
                    .path("content");

            if (content.isMissingNode() || content.asText().isBlank()) {
                throw new IllegalStateException(
                        "Groq response did not contain message content."
                );
            }

            return content.asText();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Groq API request was interrupted.",
                    e
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not communicate with Groq API.",
                    e
            );
        }
    }

    private String buildPrompt(AnalyticsSummary m) {
        return "Analyze these ShortLinkX analytics metrics:\n\n"
                + "Total clicks: " + m.getTotalClicks() + "\n"
                + "Unique visitors: " + m.getUniqueVisitors() + "\n"
                + "Mobile traffic: " + m.getMobilePercentage() + "%\n"
                + "Desktop traffic: " + m.getDesktopPercentage() + "%\n"
                + "Tablet traffic: " + m.getTabletPercentage() + "%\n"
                + "Top country: " + m.getTopCountry() + "\n"
                + "Top browser: " + m.getTopBrowser() + "\n"
                + "Growth percentage: " + m.getGrowthPercentage() + "%\n\n"
                + "Requirements:\n"
                + "1. Identify the most important observations.\n"
                + "2. Describe meaningful trends supported by the metrics.\n"
                + "3. Give exactly two practical recommendations.\n"
                + "4. Do not invent additional statistics.\n"
                + "5. Keep the response concise and suitable for a dashboard.";
    }
}
