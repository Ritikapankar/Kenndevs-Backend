package com.kenndevs.backend.newsletter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenndevs.backend.newsletter.dto.NewsletterSubscribeRequest;
import com.kenndevs.backend.newsletter.dto.NewsletterSubscribeResponse;
import com.kenndevs.backend.newsletter.service.NewsletterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NewsletterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsletterService newsletterService;

    @Test
    @DisplayName("POST /api/v1/newsletter/subscribe with valid email returns 201 Created")
    void testSubscribeSuccess() throws Exception {
        NewsletterSubscribeRequest request = NewsletterSubscribeRequest.builder()
                .email("subscriber@techweekly.io")
                .build();

        NewsletterSubscribeResponse response = NewsletterSubscribeResponse.builder()
                .email("subscriber@techweekly.io")
                .subscribedAt(Instant.now())
                .active(true)
                .build();

        when(newsletterService.subscribe(any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/newsletter/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("subscriber@techweekly.io"))
                .andExpect(jsonPath("$.data.active").value(true));
    }

    @Test
    @DisplayName("POST /api/v1/newsletter/subscribe with invalid email returns 400 Bad Request")
    void testSubscribeInvalidEmail() throws Exception {
        NewsletterSubscribeRequest request = NewsletterSubscribeRequest.builder()
                .email("not-an-email")
                .build();

        mockMvc.perform(post("/api/v1/newsletter/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("email"));
    }
}
