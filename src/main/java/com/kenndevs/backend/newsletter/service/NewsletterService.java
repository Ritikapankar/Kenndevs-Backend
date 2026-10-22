package com.kenndevs.backend.newsletter.service;

import com.kenndevs.backend.newsletter.dto.NewsletterSubscribeRequest;
import com.kenndevs.backend.newsletter.dto.NewsletterSubscribeResponse;
import com.kenndevs.backend.newsletter.entity.NewsletterSubscriber;
import com.kenndevs.backend.newsletter.repository.NewsletterSubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsletterService {

    private final NewsletterSubscriberRepository repository;

    @Transactional
    public NewsletterSubscribeResponse subscribe(NewsletterSubscribeRequest request, String clientIp) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();
        log.info("Processing newsletter subscription for: {}", maskEmail(normalizedEmail));

        Optional<NewsletterSubscriber> existing = repository.findByEmailIgnoreCase(normalizedEmail);

        NewsletterSubscriber subscriber;
        if (existing.isPresent()) {
            subscriber = existing.get();
            if (!subscriber.isActive()) {
                subscriber.setActive(true);
                subscriber.setSubscribedAt(Instant.now());
                subscriber.setIpAddress(clientIp);
                subscriber = repository.save(subscriber);
                log.info("Re-activated newsletter subscription for: {}", maskEmail(normalizedEmail));
            } else {
                log.info("Newsletter subscription already active for: {}", maskEmail(normalizedEmail));
            }
        } else {
            subscriber = NewsletterSubscriber.builder()
                    .email(normalizedEmail)
                    .active(true)
                    .ipAddress(clientIp)
                    .subscribedAt(Instant.now())
                    .build();
            subscriber = repository.save(subscriber);
            log.info("Created new newsletter subscriber for: {}", maskEmail(normalizedEmail));
        }

        return NewsletterSubscribeResponse.builder()
                .email(subscriber.getEmail())
                .subscribedAt(subscriber.getSubscribedAt())
                .active(subscriber.isActive())
                .build();
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }
        String[] parts = email.split("@");
        String name = parts[0];
        String visible = name.length() > 2 ? name.substring(0, 2) + "***" : "***";
        return visible + "@" + parts[1];
    }
}
