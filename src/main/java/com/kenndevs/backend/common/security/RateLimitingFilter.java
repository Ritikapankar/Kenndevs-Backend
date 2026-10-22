package com.kenndevs.backend.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenndevs.backend.common.exception.ApiErrorResponse;
import com.kenndevs.backend.common.util.ClientIpResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final ClientIpResolver clientIpResolver;
    private final ObjectMapper objectMapper;

    @Value("${kennstack.rate-limit.enabled:true}")
    private boolean rateLimitEnabled;

    @Value("${kennstack.rate-limit.requests-per-minute:15}")
    private int requestsPerMinute;

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";

    private final Map<String, RequestBucket> ipBuckets = new ConcurrentHashMap<>();

    private static class RequestBucket {
        long windowStartEpochMs;
        AtomicInteger count;

        RequestBucket(long now) {
            this.windowStartEpochMs = now;
            this.count = new AtomicInteger(1);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString().substring(0, 8);
        }
        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            String path = request.getRequestURI();
            String method = request.getMethod();

            boolean isRateLimitedPath = ("POST".equalsIgnoreCase(method)) &&
                    (path.contains("/contact") || path.contains("/newsletter"));

            if (rateLimitEnabled && isRateLimitedPath) {
                String clientIp = clientIpResolver.resolveClientIp(request);
                long now = System.currentTimeMillis();

                RequestBucket bucket = ipBuckets.compute(clientIp, (ip, current) -> {
                    if (current == null || (now - current.windowStartEpochMs) > 60_000) {
                        return new RequestBucket(now);
                    }
                    current.count.incrementAndGet();
                    return current;
                });

                if (bucket.count.get() > requestsPerMinute) {
                    log.warn("Rate limit exceeded for IP: {} on path: {}", clientIp, path);
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    ApiErrorResponse error = ApiErrorResponse.builder()
                            .success(false)
                            .status(HttpStatus.TOO_MANY_REQUESTS.value())
                            .error("TOO_MANY_REQUESTS")
                            .message("Too many requests from your IP. Please try again after 1 minute.")
                            .path(path)
                            .correlationId(correlationId)
                            .timestamp(Instant.now())
                            .build();

                    objectMapper.writeValue(response.getWriter(), error);
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }
}
