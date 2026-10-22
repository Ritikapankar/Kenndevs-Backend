package com.kenndevs.backend.portfolio.service;

import com.kenndevs.backend.common.exception.ResourceNotFoundException;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectDetailResponse;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectSummaryResponse;
import com.kenndevs.backend.portfolio.entity.PortfolioProject;
import com.kenndevs.backend.portfolio.mapper.PortfolioProjectMapper;
import com.kenndevs.backend.portfolio.repository.PortfolioProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioProjectService {

    private final PortfolioProjectRepository repository;
    private final PortfolioProjectMapper mapper;

    @Transactional(readOnly = true)
    public List<PortfolioProjectSummaryResponse> getAllActiveProjects(String category) {
        log.debug("Fetching active portfolio projects with category filter: {}", category);
        List<PortfolioProject> projects;
        if (category != null && !category.isBlank()) {
            projects = repository.findByCategoryIgnoreCaseAndActiveTrueOrderBySortOrderAsc(category.trim());
        } else {
            projects = repository.findAllByActiveTrueOrderBySortOrderAsc();
        }
        return projects.stream().map(mapper::toSummary).toList();
    }

    @Transactional(readOnly = true)
    public PortfolioProjectDetailResponse getProjectBySlug(String slug) {
        String cleanSlug = slug.trim().toLowerCase();
        log.debug("Fetching portfolio case study for slug: {}", cleanSlug);

        PortfolioProject project = repository.findBySlugAndActiveTrue(cleanSlug)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio case study not found for slug: " + cleanSlug));

        return mapper.toDetail(project);
    }
}
