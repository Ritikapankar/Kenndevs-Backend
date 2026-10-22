package com.kenndevs.backend.portfolio;

import com.kenndevs.backend.common.exception.ResourceNotFoundException;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectDetailResponse;
import com.kenndevs.backend.portfolio.dto.PortfolioProjectSummaryResponse;
import com.kenndevs.backend.portfolio.service.PortfolioProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PortfolioProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioProjectService portfolioProjectService;

    @Test
    @DisplayName("GET /api/v1/portfolio returns list of featured case studies")
    void testGetAllProjects() throws Exception {
        PortfolioProjectSummaryResponse project = PortfolioProjectSummaryResponse.builder()
                .id(UUID.randomUUID())
                .slug("ecommerce-overhaul")
                .title("E-commerce Platform Overhaul")
                .category("Web Development")
                .description("Revitalized platform")
                .visualType("ecommerce")
                .impactMetric("+40% Conversion Boost")
                .caseStudyLink("/portfolio/ecommerce-overhaul")
                .tags(List.of("Next.js", "GraphQL", "Stripe API"))
                .sortOrder(1)
                .build();

        when(portfolioProjectService.getAllActiveProjects(null)).thenReturn(List.of(project));

        mockMvc.perform(get("/api/v1/portfolio").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].slug").value("ecommerce-overhaul"))
                .andExpect(jsonPath("$.data[0].impactMetric").value("+40% Conversion Boost"));
    }

    @Test
    @DisplayName("GET /api/v1/portfolio/{slug} returns case study details")
    void testGetProjectBySlug() throws Exception {
        PortfolioProjectDetailResponse detail = PortfolioProjectDetailResponse.builder()
                .id(UUID.randomUUID())
                .slug("healthcare-app")
                .title("AI-Powered Biometric Healthcare Monitoring App")
                .category("Mobile & AI")
                .client("HealthTech Innovations")
                .duration("6 Months")
                .impactMetric("99.4% Diagnostic Accuracy")
                .visualType("healthcare")
                .summary("Real-time patient monitoring")
                .challenge("Sensor latency")
                .solution("Native 60 FPS React Native")
                .results(List.of("Streaming latency < 50ms"))
                .techStack(List.of("React Native", "PyTorch"))
                .build();

        when(portfolioProjectService.getProjectBySlug("healthcare-app")).thenReturn(detail);

        mockMvc.perform(get("/api/v1/portfolio/healthcare-app").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.slug").value("healthcare-app"))
                .andExpect(jsonPath("$.data.client").value("HealthTech Innovations"));
    }

    @Test
    @DisplayName("GET /api/v1/portfolio/{slug} with unknown slug returns 404")
    void testGetUnknownProject() throws Exception {
        when(portfolioProjectService.getProjectBySlug("unknown-project"))
                .thenThrow(new ResourceNotFoundException("Portfolio case study not found for slug: unknown-project"));

        mockMvc.perform(get("/api/v1/portfolio/unknown-project").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
