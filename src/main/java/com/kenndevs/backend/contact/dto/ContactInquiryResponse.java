package com.kenndevs.backend.contact.dto;

import com.kenndevs.backend.contact.entity.InquiryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Contact inquiry submission confirmation data")
public class ContactInquiryResponse {

    @Schema(description = "Unique generated inquiry ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Inquirer name", example = "Jane Doe")
    private String name;

    @Schema(description = "Inquirer email address", example = "jane.doe@example.com")
    private String email;

    @Schema(description = "Inquiry subject", example = "Enterprise Cloud Architecture Consultation")
    private String subject;

    @Schema(description = "Designated inquiry receiver email address", example = "ketanwachar2000@gmail.com")
    private String recipient;

    @Schema(description = "Current inquiry processing status", example = "DISPATCHED")
    private InquiryStatus status;

    @Schema(description = "Creation timestamp")
    private Instant createdAt;
}
