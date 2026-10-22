package com.kenndevs.backend.servicecatalog.repository;

import com.kenndevs.backend.servicecatalog.entity.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, UUID> {

    Optional<ServiceCatalog> findBySlugAndActiveTrue(String slug);

    List<ServiceCatalog> findAllByActiveTrueOrderBySortOrderAsc();

    List<ServiceCatalog> findByCategoryIgnoreCaseAndActiveTrueOrderBySortOrderAsc(String category);

    long countByActiveTrue();
}
