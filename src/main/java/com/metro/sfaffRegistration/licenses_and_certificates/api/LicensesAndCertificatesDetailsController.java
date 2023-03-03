package com.metro.sfaffRegistration.licenses_and_certificates.api;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.licenses_and_certificates.dtos.LicensesAndCertificatesDetailsDTO;
import com.metro.sfaffRegistration.licenses_and_certificates.services.LicensesAndCertificatesDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@RestController
@RequestMapping("/staff/{staff_id}/licenses_and_certificates")
@Tag(name = "LICENSES AND CERTIFICATIONS DETAILS")
public class LicensesAndCertificatesDetailsController {
    private final LicensesAndCertificatesDetailsService licensesAndCertificatesDetailsService;

    public LicensesAndCertificatesDetailsController(LicensesAndCertificatesDetailsService licensesAndCertificatesDetailsService) {
        this.licensesAndCertificatesDetailsService = licensesAndCertificatesDetailsService;
    }
    @Operation(summary = "Save Certificate or License Details of an employee.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = " Successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<?> createLicensesAndCertificationDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Licenses and Certifications.")
                .success(false)
                .data(licensesAndCertificatesDetailsDTO)
                .build();
        try {
            response = licensesAndCertificatesDetailsService.createLicensesAndCertificatesDetails(licensesAndCertificatesDetailsDTO, id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "update License and Certificate Details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<?> updateLicensesAndCertificationDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Staff Id Details")
                .success(false)
                .data(licensesAndCertificatesDetailsDTO)
                .build();
        try {
            response = licensesAndCertificatesDetailsService.updateLicensesAndCertificatesDetails(id, licensesAndCertificatesDetailsDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch  All License and certificate details of an employee")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getStaffLicensesAndCertificationDetailsById(@PathVariable("staff_id") Long staff_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get Id Details by Employee Id ")
                .success(false)
                .build();
        try {
            response = licensesAndCertificatesDetailsService.selectLicensesAndCertificatesDetailsByID(staff_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
