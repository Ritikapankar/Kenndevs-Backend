package com.kenndevs.backend.portfolio.dto;

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
@Schema(description = "Summary representation of a featured portfolio project card")
public class PortfolioProjectSummaryResponse {

    @Schema(description = "Project UUID")
    private UUID id;

    @Schema(description = "URL friendly slug identifier", example = "ecommerce-overhaul")
    private String slug;

    @Schema(description = "Project title", example = "E-commerce Platform Overhaul")
    private String title;

    @Schema(description = "Category descriptor", example = "Web Development")
    private String category;

    @Schema(description = "Brief summary description")
    private String description;

    @Schema(description = "Visual layout widget type", example = "ecommerce")
    private String visualType;

    @Schema(description = "Quantifiable impact metric badge", example = "+40% Conversion Boost")
    private String impactMetric;

    @Schema(description = "Link to internal case study", example = "/portfolio/ecommerce-overhaul")
    private String caseStudyLink;

    @Schema(description = "Optional live product link")
    private String liveLink;

    @Schema(description = "List of technology stack tags")
    private List<String> tags;

    @Schema(description = "Display sort order")
    private int sortOrder;
}
