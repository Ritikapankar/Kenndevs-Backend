package com.kenndevs.backend.servicecatalog.controller;

import com.kenndevs.backend.common.dto.ApiResponse;
import com.kenndevs.backend.servicecatalog.dto.ServiceDetailResponse;
import com.kenndevs.backend.servicecatalog.dto.ServiceSummaryResponse;
import com.kenndevs.backend.servicecatalog.service.ServiceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Tag(name = "Services Catalog", description = "Endpoints for retrieving KennStack service capabilities and detailed specs")
public class ServiceCatalogController {

    private final ServiceCatalogService service;

    @GetMapping
    @Operation(summary = "List Active Services", description = "Returns all published software services and capabilities sorted by priority")
    public ResponseEntity<ApiResponse<List<ServiceSummaryResponse>>> getAllServices(
            @Parameter(description = "Optional category filter") @RequestParam(required = false) String category,
            HttpServletRequest request) {

        List<ServiceSummaryResponse> services = service.getAllActiveServices(category);
        return ResponseEntity.ok(ApiResponse.success(services, "Services retrieved successfully", request.getRequestURI()));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get Service Details", description = "Retrieves complete dynamic specification, metrics, features, and engineering process by service slug")
    public ResponseEntity<ApiResponse<ServiceDetailResponse>> getServiceBySlug(
            @Parameter(description = "Service slug identifier", example = "web-development")
            @PathVariable
            @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase alphanumeric characters and hyphens")
            String slug,
            HttpServletRequest request) {

        ServiceDetailResponse detail = service.getServiceBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(detail, "Service details retrieved successfully", request.getRequestURI()));
    }
}
