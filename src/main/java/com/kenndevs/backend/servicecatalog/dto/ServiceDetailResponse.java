package com.kenndevs.backend.servicecatalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed representation of a specific service for /services/[slug]")
public class ServiceDetailResponse {

    @Schema(description = "Service UUID")
    private UUID id;

    @Schema(description = "Unique service slug", example = "web-development")
    private String slug;

    @Schema(description = "Full service title", example = "Custom Web Application Development")
    private String title;

    @Schema(description = "Category descriptor", example = "Web Engineering")
    private String category;

    @Schema(description = "Headline tagline", example = "Bespoke, high-performance web platforms built with Next.js 15, React, and Node.")
    private String tagline;

    @Schema(description = "Detailed service overview narrative")
    private String description;

    @Schema(description = "Metrics badge label", example = "Ultra Fast")
    private String metricsBadge;

    @Schema(description = "List of technology stack tags")
    private List<String> tags;

    @Schema(description = "Key deliverables and features")
    private List<String> features;

    @Schema(description = "Quantifiable performance metrics")
    private List<MetricItem> metrics;

    @Schema(description = "Step-by-step delivery process")
    private List<ProcessStep> process;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Key performance metric badge pair")
    public static class MetricItem {
        private String label;
        private String val;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Process milestone phase")
    public static class ProcessStep {
        private String step;
        private String title;
        private String desc;
    }
}
