package com.kenndevs.backend.newsletter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Newsletter subscription confirmation details")
public class NewsletterSubscribeResponse {

    @Schema(description = "Subscribed email address", example = "engineer@enterprise.com")
    private String email;

    @Schema(description = "Timestamp of subscription")
    private Instant subscribedAt;

    @Schema(description = "Whether the subscription is currently active", example = "true")
    private boolean active;
}
