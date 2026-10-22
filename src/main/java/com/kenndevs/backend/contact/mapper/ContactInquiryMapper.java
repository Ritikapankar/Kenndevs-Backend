package com.kenndevs.backend.contact.mapper;

import com.kenndevs.backend.contact.dto.ContactInquiryRequest;
import com.kenndevs.backend.contact.dto.ContactInquiryResponse;
import com.kenndevs.backend.contact.entity.ContactInquiry;
import com.kenndevs.backend.contact.entity.InquiryStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ContactInquiryMapper {

    public ContactInquiry toEntity(ContactInquiryRequest request, String recipientEmail, String ipAddress, String userAgent) {
        return ContactInquiry.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .subject(request.getSubject().trim())
                .message(request.getMessage().trim())
                .recipientEmail(recipientEmail)
                .status(InquiryStatus.DISPATCHED)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .dispatchedAt(Instant.now())
                .build();
    }

    public ContactInquiryResponse toResponse(ContactInquiry entity) {
        return ContactInquiryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .subject(entity.getSubject())
                .recipient(entity.getRecipientEmail())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
