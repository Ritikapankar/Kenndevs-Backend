package com.kenndevs.backend.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenndevs.backend.contact.dto.ContactInquiryRequest;
import com.kenndevs.backend.contact.dto.ContactInquiryResponse;
import com.kenndevs.backend.contact.entity.InquiryStatus;
import com.kenndevs.backend.contact.service.ContactInquiryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactInquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactInquiryService contactInquiryService;

    @Test
    @DisplayName("POST /api/v1/contact-inquiries with valid payload returns 201 Created")
    void testSubmitValidInquiry() throws Exception {
        ContactInquiryRequest request = ContactInquiryRequest.builder()
                .name("Alice Tech")
                .email("alice@techcorp.com")
                .subject("Cloud Architecture Inquiry")
                .message("We need assistance designing our zero-trust Kubernetes mesh.")
                .build();

        ContactInquiryResponse response = ContactInquiryResponse.builder()
                .id(UUID.randomUUID())
                .name("Alice Tech")
                .email("alice@techcorp.com")
                .subject("Cloud Architecture Inquiry")
                .recipient("ketanwachar2000@gmail.com")
                .status(InquiryStatus.DISPATCHED)
                .createdAt(Instant.now())
                .build();

        when(contactInquiryService.submitInquiry(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/contact-inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("alice@techcorp.com"))
                .andExpect(jsonPath("$.data.recipient").value("ketanwachar2000@gmail.com"))
                .andExpect(jsonPath("$.data.status").value("DISPATCHED"));
    }

    @Test
    @DisplayName("POST /api/v1/contact drop-in alias returns 201 Created")
    void testSubmitValidInquiryToAliasRoute() throws Exception {
        ContactInquiryRequest request = ContactInquiryRequest.builder()
                .name("Bob Architect")
                .email("bob@cloud.com")
                .subject("Migration Consultation")
                .message("Planning legacy system overhaul into Next.js and microservices.")
                .build();

        ContactInquiryResponse response = ContactInquiryResponse.builder()
                .id(UUID.randomUUID())
                .name("Bob Architect")
                .email("bob@cloud.com")
                .subject("Migration Consultation")
                .recipient("ketanwachar2000@gmail.com")
                .status(InquiryStatus.DISPATCHED)
                .createdAt(Instant.now())
                .build();

        when(contactInquiryService.submitInquiry(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Bob Architect"));
    }

    @Test
    @DisplayName("POST /api/v1/contact-inquiries with invalid email returns 400 Bad Request")
    void testSubmitInvalidEmailInquiry() throws Exception {
        ContactInquiryRequest request = ContactInquiryRequest.builder()
                .name("Alice")
                .email("not-a-valid-email")
                .subject("General Inquiry")
                .message("Testing invalid email payload validation.")
                .build();

        mockMvc.perform(post("/api/v1/contact-inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }

    @Test
    @DisplayName("POST /api/v1/contact-inquiries with short message returns 400 Bad Request")
    void testSubmitShortMessageInquiry() throws Exception {
        ContactInquiryRequest request = ContactInquiryRequest.builder()
                .name("Alice")
                .email("alice@techcorp.com")
                .subject("Inquiry")
                .message("Too short")
                .build();

        mockMvc.perform(post("/api/v1/contact-inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("message"));
    }
}
