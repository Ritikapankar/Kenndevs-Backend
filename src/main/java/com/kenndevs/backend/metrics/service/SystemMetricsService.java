package com.kenndevs.backend.metrics.service;

import com.kenndevs.backend.contact.repository.ContactInquiryRepository;
import com.kenndevs.backend.metrics.dto.SystemMetricsResponse;
import com.kenndevs.backend.newsletter.repository.NewsletterSubscriberRepository;
import com.kenndevs.backend.portfolio.repository.PortfolioProjectRepository;
import com.kenndevs.backend.servicecatalog.repository.ServiceCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemMetricsService {

    private final ContactInquiryRepository inquiryRepository;
    private final NewsletterSubscriberRepository newsletterRepository;
    private final ServiceCatalogRepository serviceRepository;
    private final PortfolioProjectRepository portfolioRepository;

    @Transactional(readOnly = true)
    public SystemMetricsResponse getRealSystemMetrics() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        long uptimeSeconds = runtimeBean.getUptime() / 1000;

        Runtime runtime = Runtime.getRuntime();
        double totalMemoryMb = runtime.totalMemory() / (1024.0 * 1024.0);
        double freeMemoryMb = runtime.freeMemory() / (1024.0 * 1024.0);
        double usedMemoryMb = Math.round((totalMemoryMb - freeMemoryMb) * 100.0) / 100.0;
        double maxMemoryMb = Math.round((runtime.maxMemory() / (1024.0 * 1024.0)) * 100.0) / 100.0;

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        int activeThreads = threadBean.getThreadCount();
        int processors = runtime.availableProcessors();

        long inquiryCount = inquiryRepository.count();
        long newsletterCount = newsletterRepository.countByActiveTrue();
        long serviceCount = serviceRepository.countByActiveTrue();
        long portfolioCount = portfolioRepository.countByActiveTrue();

        return SystemMetricsResponse.builder()
                .application("kennstack-backend")
                .version("1.0.0")
                .status("OPERATIONAL")
                .uptimeSeconds(uptimeSeconds)
                .totalContactInquiries(inquiryCount)
                .totalNewsletterSubscribers(newsletterCount)
                .activeServicesCount(serviceCount)
                .activePortfolioProjectsCount(portfolioCount)
                .jvmMemoryUsedMb(usedMemoryMb)
                .jvmMaxMemoryMb(maxMemoryMb)
                .availableProcessors(processors)
                .activeThreads(activeThreads)
                .timestamp(Instant.now())
                .build();
    }
}
