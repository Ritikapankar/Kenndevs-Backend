package com.kenndevs.backend.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "portfolio_projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioProject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 80)
    private String slug;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "client_name", nullable = false, length = 150)
    private String clientName;

    @Column(nullable = false, length = 50)
    private String duration;

    @Column(name = "impact_metric", nullable = false, length = 100)
    private String impactMetric;

    @Column(name = "visual_type", nullable = false, length = 50)
    private String visualType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String challenge;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String solution;

    @Column(name = "results_json", nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String resultsJson = "[]";

    @Column(name = "tech_stack_json", nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String techStackJson = "[]";

    @Column(name = "live_link", length = 255)
    private String liveLink;

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
