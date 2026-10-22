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
@Schema(description = "Summary representation of a service card for listing and home highlights")
public class ServiceSummaryResponse {

    @Schema(description = "Service UUID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "URL friendly slug identifier", example = "web-development")
    private String slug;

    @Schema(description = "Service title", example = "Custom Web Development")
    private String title;

    @Schema(description = "Broad category", example = "Web Engineering")
    private String category;

    @Schema(description = "Brief punchy tagline", example = "Bespoke, high-performance web platforms built with Next.js 15, React, and Node.")
    private String tagline;

    @Schema(description = "Card summary description")
    private String description;

    @Schema(description = "Badge label displayed on card", example = "Ultra Fast")
    private String metricsBadge;

    @Schema(description = "Navigation link to details", example = "/services/web-development")
    private String learnMoreLink;

    @Schema(description = "Technology tags associated with the capability")
    private List<String> tags;

    @Schema(description = "Sort order for UI alignment")
    private int sortOrder;
}
