package com.kenndevs.backend.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {
    @Schema(description = "Indicates request success", example = "true")
    private boolean success;

    @Schema(description = "Payload data")
    private T data;

    @Schema(description = "Human readable message", example = "Operation successful")
    private String message;

    @Schema(description = "Request URI path", example = "/api/v1/contact-inquiries")
    private String path;

    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String path) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .path(path)
                .build();
    }
}
