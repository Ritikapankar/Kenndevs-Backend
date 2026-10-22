package com.kenndevs.backend.servicecatalog.service;

import com.kenndevs.backend.common.exception.ResourceNotFoundException;
import com.kenndevs.backend.servicecatalog.dto.ServiceDetailResponse;
import com.kenndevs.backend.servicecatalog.dto.ServiceSummaryResponse;
import com.kenndevs.backend.servicecatalog.entity.ServiceCatalog;
import com.kenndevs.backend.servicecatalog.mapper.ServiceCatalogMapper;
import com.kenndevs.backend.servicecatalog.repository.ServiceCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceCatalogService {

    private final ServiceCatalogRepository repository;
    private final ServiceCatalogMapper mapper;

    @Transactional(readOnly = true)
    public List<ServiceSummaryResponse> getAllActiveServices(String category) {
        log.debug("Fetching active services with optional category: {}", category);
        List<ServiceCatalog> services;
        if (category != null && !category.isBlank()) {
            services = repository.findByCategoryIgnoreCaseAndActiveTrueOrderBySortOrderAsc(category.trim());
        } else {
            services = repository.findAllByActiveTrueOrderBySortOrderAsc();
        }
        return services.stream().map(mapper::toSummary).toList();
    }

    @Transactional(readOnly = true)
    public ServiceDetailResponse getServiceBySlug(String slug) {
        String cleanSlug = slug.trim().toLowerCase();
        log.debug("Fetching service details for slug: {}", cleanSlug);

        ServiceCatalog service = repository.findBySlugAndActiveTrue(cleanSlug)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found for slug: " + cleanSlug));

        return mapper.toDetail(service);
    }

    @Transactional(readOnly = true)
    public long countActiveServices() {
        return repository.countByActiveTrue();
    }
}
