package com.kenndevs.backend.portfolio.controller;

import com.kenndevs.backend.common.dto.ApiResponse;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectDetailResponse;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectSummaryResponse;
import com.kenndevs.backend.portfolio.service.PortfolioProjectService;
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
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
@Tag(name = "Portfolio & Case Studies", description = "Endpoints for featured enterprise case studies and quantifiable impact metrics")
public class PortfolioProjectController {

    private final PortfolioProjectService service;

    @GetMapping
    @Operation(summary = "List Featured Projects", description = "Returns all published case studies with summary cards and tags")
    public ResponseEntity<ApiResponse<List<PortfolioProjectSummaryResponse>>> getAllProjects(
            @Parameter(description = "Optional category filter") @RequestParam(required = false) String category,
            HttpServletRequest request) {

        List<PortfolioProjectSummaryResponse> projects = service.getAllActiveProjects(category);
        return ResponseEntity.ok(ApiResponse.success(projects, "Portfolio projects retrieved successfully", request.getRequestURI()));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get Project Case Study", description = "Retrieves full case study narrative, challenge, solution, and quantifiable results")
    public ResponseEntity<ApiResponse<PortfolioProjectDetailResponse>> getProjectBySlug(
            @Parameter(description = "Project slug identifier", example = "ecommerce-overhaul")
            @PathVariable
            @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase alphanumeric characters and hyphens")
            String slug,
            HttpServletRequest request) {

        PortfolioProjectDetailResponse detail = service.getProjectBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(detail, "Project case study retrieved successfully", request.getRequestURI()));
    }
}
