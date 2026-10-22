package com.kenndevs.backend.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import com.kenndevs.backend.common.dto.FieldValidationError;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard centralized API error structure")
public class ApiErrorResponse {

    @Schema(description = "Indicates request failure", example = "false")
    @Builder.Default
    private boolean success = false;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error code identifier", example = "BAD_REQUEST")
    private String error;

    @Schema(description = "User-safe error explanation", example = "Validation failed for request payload")
    private String message;

    @Schema(description = "Request URI path", example = "/api/v1/contact-inquiries")
    private String path;

    @Schema(description = "List of invalid fields when validation fails")
    private List<FieldValidationError> fieldErrors;

    @Schema(description = "Distributed trace or correlation ID for diagnostics", example = "req-4a8bc39d")
    private String correlationId;

    @Schema(description = "Timestamp when the error occurred")
    @Builder.Default
    private Instant timestamp = Instant.now();
}
