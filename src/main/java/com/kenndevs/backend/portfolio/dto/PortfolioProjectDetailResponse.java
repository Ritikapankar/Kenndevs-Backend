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
@Schema(description = "Detailed case study representation for /portfolio/[slug]")
public class PortfolioProjectDetailResponse {

    @Schema(description = "Project UUID")
    private UUID id;

    @Schema(description = "Case study slug identifier", example = "ecommerce-overhaul")
    private String slug;

    @Schema(description = "Full case study title", example = "E-commerce Platform Overhaul & Checkout Acceleration")
    private String title;

    @Schema(description = "Category descriptor", example = "Web Development")
    private String category;

    @Schema(description = "Client industry or name", example = "Global Retail Enterprise")
    private String client;

    @Schema(description = "Project timeline duration", example = "4 Months")
    private String duration;

    @Schema(description = "Key quantifiable impact metric", example = "+40.2% Revenue Boost")
    private String impactMetric;

    @Schema(description = "Visual widget type identifier", example = "ecommerce")
    private String visualType;

    @Schema(description = "Executive summary")
    private String summary;

    @Schema(description = "Business & technical challenge narrative")
    private String challenge;

    @Schema(description = "Engineering solution narrative")
    private String solution;

    @Schema(description = "List of quantifiable outcomes & results")
    private List<String> results;

    @Schema(description = "List of technologies applied")
    private List<String> techStack;

    @Schema(description = "Optional external live link")
    private String liveLink;
}
