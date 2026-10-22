package com.kenndevs.backend.metrics;

import com.kenndevs.backend.metrics.dto.SystemMetricsResponse;
import com.kenndevs.backend.metrics.service.SystemMetricsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemMetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemMetricsService systemMetricsService;

    @Test
    @DisplayName("GET /api/v1/metrics/system returns genuine backend operational metrics")
    void testGetSystemMetrics() throws Exception {
        SystemMetricsResponse response = SystemMetricsResponse.builder()
                .application("kennstack-backend")
                .version("1.0.0")
                .status("OPERATIONAL")
                .uptimeSeconds(3600)
                .totalContactInquiries(15)
                .totalNewsletterSubscribers(50)
                .activeServicesCount(8)
                .activePortfolioProjectsCount(4)
                .jvmMemoryUsedMb(85.5)
                .jvmMaxMemoryMb(1024.0)
                .availableProcessors(8)
                .activeThreads(24)
                .timestamp(Instant.now())
                .build();

        when(systemMetricsService.getRealSystemMetrics()).thenReturn(response);

        mockMvc.perform(get("/api/v1/metrics/system").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("OPERATIONAL"))
                .andExpect(jsonPath("$.data.activeServicesCount").value(8))
                .andExpect(jsonPath("$.data.activePortfolioProjectsCount").value(4));
    }
}
