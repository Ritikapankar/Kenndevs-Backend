package com.kenndevs.backend.servicecatalog.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenndevs.backend.servicecatalog.dto.ServiceDetailResponse;
import com.kenndevs.backend.servicecatalog.dto.ServiceDetailResponse.MetricItem;
import com.kenndevs.backend.servicecatalog.dto.ServiceDetailResponse.ProcessStep;
import com.kenndevs.backend.servicecatalog.dto.ServiceSummaryResponse;
import com.kenndevs.backend.servicecatalog.entity.ServiceCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceCatalogMapper {

    private final ObjectMapper objectMapper;

    public ServiceSummaryResponse toSummary(ServiceCatalog entity) {
        return ServiceSummaryResponse.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .category(entity.getCategory())
                .tagline(entity.getTagline())
                .description(entity.getDescription())
                .metricsBadge(entity.getMetricsBadge())
                .learnMoreLink("/services/" + entity.getSlug())
                .tags(parseJsonList(entity.getTagsJson(), new TypeReference<List<String>>() {}))
                .sortOrder(entity.getSortOrder())
                .build();
    }

    public ServiceDetailResponse toDetail(ServiceCatalog entity) {
        return ServiceDetailResponse.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .category(entity.getCategory())
                .tagline(entity.getTagline())
                .description(entity.getDescription())
                .metricsBadge(entity.getMetricsBadge())
                .tags(parseJsonList(entity.getTagsJson(), new TypeReference<List<String>>() {}))
                .features(parseJsonList(entity.getFeaturesJson(), new TypeReference<List<String>>() {}))
                .metrics(parseJsonList(entity.getMetricsJson(), new TypeReference<List<MetricItem>>() {}))
                .process(parseJsonList(entity.getProcessJson(), new TypeReference<List<ProcessStep>>() {}))
                .build();
    }

    private <T> List<T> parseJsonList(String json, TypeReference<List<T>> typeReference) {
        if (json == null || json.isBlank() || "[]".equals(json.trim())) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.warn("Failed to parse JSON string: {}", json, e);
            return Collections.emptyList();
        }
    }
}
