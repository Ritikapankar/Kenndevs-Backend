package com.kenndevs.backend.newsletter.controller;

import com.kenndevs.backend.common.dto.ApiResponse;
import com.kenndevs.backend.common.util.ClientIpResolver;
import com.kenndevs.backend.newsletter.dto.NewsletterSubscribeRequest;
import com.kenndevs.backend.newsletter.dto.NewsletterSubscribeResponse;
import com.kenndevs.backend.newsletter.service.NewsletterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/newsletter")
@RequiredArgsConstructor
@Tag(name = "Newsletter", description = "Endpoints for newsletter subscriptions from the engineering blog")
public class NewsletterController {

    private final NewsletterService service;
    private final ClientIpResolver clientIpResolver;

    @PostMapping("/subscribe")
    @Operation(summary = "Subscribe to Newsletter", description = "Registers an email address for weekly engineering newsletters")
    public ResponseEntity<ApiResponse<NewsletterSubscribeResponse>> subscribe(
            @Valid @RequestBody NewsletterSubscribeRequest request,
            HttpServletRequest servletRequest) {

        String clientIp = clientIpResolver.resolveClientIp(servletRequest);
        NewsletterSubscribeResponse response = service.subscribe(request, clientIp);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(response, "Subscribed successfully! Welcome to the KennStack Tech Radar.", servletRequest.getRequestURI())
        );
    }
}
