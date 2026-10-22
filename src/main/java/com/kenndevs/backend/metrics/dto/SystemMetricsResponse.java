package com.kenndevs.backend.metrics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Real backend operational health and aggregated business telemetry")
public class SystemMetricsResponse {

    @Schema(description = "Application name", example = "kennstack-backend")
    private String application;

    @Schema(description = "Current software version", example = "1.0.0")
    private String version;

    @Schema(description = "Overall backend operational status", example = "OPERATIONAL")
    private String status;

    @Schema(description = "Backend uptime in seconds", example = "86400")
    private long uptimeSeconds;

    @Schema(description = "Total contact inquiries recorded in database", example = "42")
    private long totalContactInquiries;

    @Schema(description = "Total active newsletter subscribers in database", example = "128")
    private long totalNewsletterSubscribers;

    @Schema(description = "Total published services in catalog", example = "8")
    private long activeServicesCount;

    @Schema(description = "Total featured portfolio case studies", example = "4")
    private long activePortfolioProjectsCount;

    @Schema(description = "JVM memory used in Megabytes", example = "142.5")
    private double jvmMemoryUsedMb;

    @Schema(description = "JVM max memory allocated in Megabytes", example = "1024.0")
    private double jvmMaxMemoryMb;

    @Schema(description = "Available CPU processors", example = "8")
    private int availableProcessors;

    @Schema(description = "Active JVM thread count", example = "28")
    private int activeThreads;

    @Schema(description = "Telemetry snapshot timestamp")
    private Instant timestamp;
}
