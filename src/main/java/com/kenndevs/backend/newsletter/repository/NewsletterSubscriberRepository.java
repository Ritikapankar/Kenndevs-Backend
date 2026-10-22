package com.kenndevs.backend.newsletter.repository;

import com.kenndevs.backend.newsletter.entity.NewsletterSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, UUID> {

    Optional<NewsletterSubscriber> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    long countByActiveTrue();
}
