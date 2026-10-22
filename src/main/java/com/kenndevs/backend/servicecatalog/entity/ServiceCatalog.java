package com.kenndevs.backend.servicecatalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "service_catalog")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 80)
    private String slug;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, length = 255)
    private String tagline;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "metrics_badge", length = 50)
    private String metricsBadge;

    @Column(name = "tags_json", nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String tagsJson = "[]";

    @Column(name = "features_json", nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String featuresJson = "[]";

    @Column(name = "metrics_json", nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String metricsJson = "[]";

    @Column(name = "process_json", nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String processJson = "[]";

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private int sortOrder = 0;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
