package com.kenndevs.backend.contact;

import com.kenndevs.backend.contact.dto.ContactInquiryRequest;
import com.kenndevs.backend.contact.dto.ContactInquiryResponse;
import com.kenndevs.backend.contact.entity.ContactInquiry;
import com.kenndevs.backend.contact.entity.InquiryStatus;
import com.kenndevs.backend.contact.mapper.ContactInquiryMapper;
import com.kenndevs.backend.contact.repository.ContactInquiryRepository;
import com.kenndevs.backend.contact.service.ContactInquiryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactInquiryServiceTest {

    @Mock
    private ContactInquiryRepository repository;

    @Spy
    private ContactInquiryMapper mapper;

    @InjectMocks
    private ContactInquiryService service;

    @Test
    @DisplayName("submitInquiry properly maps entity, persists to repository, and returns response")
    void testSubmitInquirySuccess() {
        ReflectionTestUtils.setField(service, "recipientEmail", "ketanwachar2000@gmail.com");

        ContactInquiryRequest request = ContactInquiryRequest.builder()
                .name("Ketan Dev")
                .email("test@kennstack.com")
                .subject("Enterprise Demo")
                .message("Requesting an in-depth architectural audit.")
                .build();

        UUID inquiryId = UUID.randomUUID();
        ContactInquiry savedEntity = ContactInquiry.builder()
                .id(inquiryId)
                .name("Ketan Dev")
                .email("test@kennstack.com")
                .subject("Enterprise Demo")
                .message("Requesting an in-depth architectural audit.")
                .recipientEmail("ketanwachar2000@gmail.com")
                .status(InquiryStatus.DISPATCHED)
                .createdAt(Instant.now())
                .build();

        when(repository.save(any(ContactInquiry.class))).thenReturn(savedEntity);

        ContactInquiryResponse response = service.submitInquiry(request, "127.0.0.1", "JUnit5");

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(inquiryId);
        assertThat(response.getEmail()).isEqualTo("test@kennstack.com");
        assertThat(response.getRecipient()).isEqualTo("ketanwachar2000@gmail.com");
        assertThat(response.getStatus()).isEqualTo(InquiryStatus.DISPATCHED);

        verify(repository).save(any(ContactInquiry.class));
    }
}
