package com.kenndevs.backend.contact.service;

import com.kenndevs.backend.common.exception.ResourceNotFoundException;
import com.kenndevs.backend.contact.dto.ContactInquiryRequest;
import com.kenndevs.backend.contact.dto.ContactInquiryResponse;
import com.kenndevs.backend.contact.entity.ContactInquiry;
import com.kenndevs.backend.contact.mapper.ContactInquiryMapper;
import com.kenndevs.backend.contact.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactInquiryService {

    private final ContactInquiryRepository repository;
    private final ContactInquiryMapper mapper;

    @Value("${kennstack.notifications.recipient-email:ketanwachar2000@gmail.com}")
    private String recipientEmail;

    @Transactional
    public ContactInquiryResponse submitInquiry(ContactInquiryRequest request, String clientIp, String userAgent) {
        log.info("Processing contact inquiry from: {} <{}> for recipient: {}",
                request.getName(), maskEmail(request.getEmail()), recipientEmail);

        ContactInquiry entity = mapper.toEntity(request, recipientEmail, clientIp, userAgent);
        ContactInquiry saved = repository.save(entity);

        // Structured audit dispatch log matching the frontend route log format
        log.info("==========================================");
        log.info("NEW CONTACT INQUIRY DISPATCHED TO: {}", recipientEmail);
        log.info("Inquiry ID: {}", saved.getId());
        log.info("From: {} <{}>", saved.getName(), saved.getEmail());
        log.info("Subject: {}", saved.getSubject());
        log.info("IP: {}", clientIp != null ? clientIp : "N/A");
        log.info("==========================================");

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ContactInquiryResponse getInquiryById(UUID id) {
        ContactInquiry inquiry = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact inquiry not found with ID: " + id));
        return mapper.toResponse(inquiry);
    }

    @Transactional(readOnly = true)
    public Page<ContactInquiryResponse> getAllInquiries(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
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
