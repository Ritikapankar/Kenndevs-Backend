package com.kenndevs.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a field validation error with field name and default message")
public class FieldValidationError {
    @Schema(description = "Name of the field that failed validation", example = "email")
    private String field;

    @Schema(description = "Validation error message", example = "must be a well-formed email address")
    private String message;
}
