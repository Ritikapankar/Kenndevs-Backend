package com.kenndevs.backend.contact.controller;

import com.kenndevs.backend.common.dto.ApiResponse;
import com.kenndevs.backend.common.util.ClientIpResolver;
import com.kenndevs.backend.contact.dto.ContactInquiryRequest;
import com.kenndevs.backend.contact.dto.ContactInquiryResponse;
import com.kenndevs.backend.contact.service.ContactInquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Contact & Inquiries", description = "Endpoints for submitting and managing client project inquiries")
public class ContactInquiryController {

    private final ContactInquiryService service;
    private final ClientIpResolver clientIpResolver;

    @PostMapping({"/contact-inquiries", "/contact"})
    @Operation(
            summary = "Submit Contact Inquiry",
            description = "Receives a project consultation or contact message from the KennStack frontend website, validates input fields, persists to PostgreSQL, and logs notification dispatch."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Inquiry successfully recorded and dispatched",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation failure or malformed payload"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "429",
                    description = "Rate limit exceeded (too many inquiries from client IP)"
            )
    })
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> submitInquiry(
            @Valid @RequestBody ContactInquiryRequest request,
            HttpServletRequest servletRequest) {

        String clientIp = clientIpResolver.resolveClientIp(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        ContactInquiryResponse responseData = service.submitInquiry(request, clientIp, userAgent);

        ApiResponse<ContactInquiryResponse> apiResponse = ApiResponse.success(
                responseData,
                "Inquiry successfully logged and dispatched to " + responseData.getRecipient(),
                servletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/contact-inquiries/{id}")
    @Operation(summary = "Get Inquiry By ID", description = "Retrieves stored details of a submitted inquiry by its UUID")
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> getInquiryById(
            @Parameter(description = "UUID of the inquiry") @PathVariable UUID id,
            HttpServletRequest servletRequest) {

        ContactInquiryResponse responseData = service.getInquiryById(id);
        return ResponseEntity.ok(ApiResponse.success(responseData, "Inquiry retrieved", servletRequest.getRequestURI()));
    }
}
