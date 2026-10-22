package com.kenndevs.backend.newsletter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Newsletter subscription request from KennStack blog")
public class NewsletterSubscribeRequest {

    @NotBlank(message = "Email address is required.")
    @Email(message = "Invalid email address format.")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    @Schema(description = "Subscriber email address", example = "engineer@enterprise.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
