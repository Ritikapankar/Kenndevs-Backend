package com.kenndevs.backend.metrics.controller;

import com.kenndevs.backend.common.dto.ApiResponse;
import com.kenndevs.backend.metrics.dto.SystemMetricsResponse;
import com.kenndevs.backend.metrics.service.SystemMetricsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
@Tag(name = "System Metrics", description = "Endpoints providing genuine operational health and aggregate business measurements")
public class SystemMetricsController {

    private final SystemMetricsService service;

    @GetMapping("/system")
    @Operation(summary = "Get Real Operational Metrics", description = "Returns verified backend runtime status, uptime, database aggregates, and JVM memory measurements")
    public ResponseEntity<ApiResponse<SystemMetricsResponse>> getSystemMetrics(HttpServletRequest request) {
        SystemMetricsResponse metrics = service.getRealSystemMetrics();
        return ResponseEntity.ok(ApiResponse.success(metrics, "System metrics retrieved successfully", request.getRequestURI()));
    }
}
