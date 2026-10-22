package com.kenndevs.backend.portfolio.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectDetailResponse;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectSummaryResponse;
import com.kenndevs.backend.portfolio.entity.PortfolioProject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortfolioProjectMapper {

    private final ObjectMapper objectMapper;

    public PortfolioProjectSummaryResponse toSummary(PortfolioProject entity) {
        return PortfolioProjectSummaryResponse.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .category(entity.getCategory())
                .description(entity.getSummary())
                .visualType(entity.getVisualType())
                .impactMetric(entity.getImpactMetric())
                .caseStudyLink("/portfolio/" + entity.getSlug())
                .liveLink(entity.getLiveLink())
                .tags(parseJsonList(entity.getTechStackJson()))
                .sortOrder(entity.getSortOrder())
                .build();
    }

    public PortfolioProjectDetailResponse toDetail(PortfolioProject entity) {
        return PortfolioProjectDetailResponse.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .category(entity.getCategory())
                .client(entity.getClientName())
                .duration(entity.getDuration())
                .impactMetric(entity.getImpactMetric())
                .visualType(entity.getVisualType())
                .summary(entity.getSummary())
                .challenge(entity.getChallenge())
                .solution(entity.getSolution())
                .results(parseJsonList(entity.getResultsJson()))
                .techStack(parseJsonList(entity.getTechStackJson()))
                .liveLink(entity.getLiveLink())
                .build();
    }

    private List<String> parseJsonList(String json) {
        if (json == null || json.isBlank() || "[]".equals(json.trim())) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            log.warn("Failed to parse JSON string: {}", json, e);
            return Collections.emptyList();
        }
    }
}
