package com.kenndevs.backend.contact.dto;

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
@Schema(description = "Contact inquiry submission payload from KennStack website")
public class ContactInquiryRequest {

    @NotBlank(message = "Name must be at least 2 characters.")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    @Schema(description = "Inquirer full name", example = "Jane Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Invalid email address.")
    @Email(message = "Invalid email address.")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    @Schema(description = "Inquirer email address", example = "jane.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Subject must be at least 3 characters.")
    @Size(min = 3, max = 200, message = "Subject must be between 3 and 200 characters.")
    @Schema(description = "Inquiry subject line", example = "Enterprise Cloud Architecture Consultation", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subject;

    @NotBlank(message = "Message must be at least 10 characters.")
    @Size(min = 10, max = 1000, message = "Message must be between 10 and 1000 characters.")
    @Schema(description = "Detailed project requirements or question", example = "We need assistance architecting our AWS multi-region Kubernetes cluster.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;
}
