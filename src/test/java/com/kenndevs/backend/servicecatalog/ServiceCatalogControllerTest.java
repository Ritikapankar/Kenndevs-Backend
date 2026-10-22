package com.kenndevs.backend.servicecatalog;

import com.kenndevs.backend.common.exception.ResourceNotFoundException;
import com.kenndevs.backend.servicecatalog.dto.ServiceDetailResponse;
import com.kenndevs.backend.servicecatalog.dto.ServiceSummaryResponse;
import com.kenndevs.backend.servicecatalog.service.ServiceCatalogService;
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
class ServiceCatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceCatalogService serviceCatalogService;

    @Test
    @DisplayName("GET /api/v1/services returns list of active services")
    void testGetAllServices() throws Exception {
        ServiceSummaryResponse service = ServiceSummaryResponse.builder()
                .id(UUID.randomUUID())
                .slug("web-development")
                .title("Custom Web Development")
                .category("Web Engineering")
                .tagline("High performance web apps")
                .description("Next.js enterprise apps")
                .metricsBadge("Ultra Fast")
                .learnMoreLink("/services/web-development")
                .tags(List.of("Next.js", "React", "TypeScript"))
                .sortOrder(1)
                .build();

        when(serviceCatalogService.getAllActiveServices(null)).thenReturn(List.of(service));

        mockMvc.perform(get("/api/v1/services").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].slug").value("web-development"))
                .andExpect(jsonPath("$.data[0].title").value("Custom Web Development"));
    }

    @Test
    @DisplayName("GET /api/v1/services/{slug} with valid slug returns details")
    void testGetServiceBySlug() throws Exception {
        ServiceDetailResponse detail = ServiceDetailResponse.builder()
                .id(UUID.randomUUID())
                .slug("cloud-solutions")
                .title("Cloud Infrastructure & Engineering")
                .category("Cloud Architecture")
                .tagline("Multi-cloud architecture and Kubernetes scaling")
                .description("Detailed description")
                .metricsBadge("99.99% SLA")
                .tags(List.of("AWS", "Kubernetes", "Docker"))
                .features(List.of("Zero-downtime blue/green deployments"))
                .build();

        when(serviceCatalogService.getServiceBySlug("cloud-solutions")).thenReturn(detail);

        mockMvc.perform(get("/api/v1/services/cloud-solutions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.slug").value("cloud-solutions"))
                .andExpect(jsonPath("$.data.metricsBadge").value("99.99% SLA"));
    }

    @Test
    @DisplayName("GET /api/v1/services/{slug} with non-existent slug returns 404 Not Found")
    void testGetNonExistentService() throws Exception {
        when(serviceCatalogService.getServiceBySlug("unknown-service"))
                .thenThrow(new ResourceNotFoundException("Service not found for slug: unknown-service"));

        mockMvc.perform(get("/api/v1/services/unknown-service").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
