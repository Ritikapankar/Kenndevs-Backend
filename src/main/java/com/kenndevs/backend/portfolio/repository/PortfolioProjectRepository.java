package com.kenndevs.backend.portfolio.repository;

import com.kenndevs.backend.portfolio.entity.PortfolioProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PortfolioProjectRepository extends JpaRepository<PortfolioProject, UUID> {

    Optional<PortfolioProject> findBySlugAndActiveTrue(String slug);

    List<PortfolioProject> findAllByActiveTrueOrderBySortOrderAsc();

    List<PortfolioProject> findByCategoryIgnoreCaseAndActiveTrueOrderBySortOrderAsc(String category);

    long countByActiveTrue();
}
