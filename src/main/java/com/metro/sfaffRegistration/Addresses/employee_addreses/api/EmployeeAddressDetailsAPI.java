package com.metro.sfaffRegistration.Addresses.employee_addreses.api;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.Addresses.employee_addreses.dtos.EmployeeAddressDetailsDTO;
import com.metro.sfaffRegistration.Addresses.employee_addreses.services.EmployeeAddressDetailsService;
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
@RequestMapping("/staff/{staff_id}/address_details")
@Tag(name = "Employee Address API")
public class EmployeeAddressDetailsAPI {
    private final EmployeeAddressDetailsService employeeAddressDetailsService;

    public EmployeeAddressDetailsAPI(EmployeeAddressDetailsService employeeAddressDetailsService) {
        this.employeeAddressDetailsService = employeeAddressDetailsService;
    }

    @Operation(summary = "Save Address of an employee in the database")
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
    public ResponseEntity<?> createStaffAddressDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid EmployeeAddressDetailsDTO employeeAddressDetailsDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Address Details.")
                .success(false)
                .data(employeeAddressDetailsDTO)
                .build();
        try {
            response = employeeAddressDetailsService.createEmployeeAddressDetails(employeeAddressDetailsDTO, id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "update Address Details")
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
    @PutMapping("/{address_id}")
    public ResponseEntity<?> updateAddressDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid EmployeeAddressDetailsDTO employeeAddressDetailsDTO, @PathVariable("address_id") Long address_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Address Details")
                .success(false)
                .data(employeeAddressDetailsDTO)
                .build();
        try {
            response = employeeAddressDetailsService.updateEmployeeAddressDetails(id, employeeAddressDetailsDTO, address_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch Address Details based on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getAddressDetailsById(@PathVariable("staff_id") Long staff_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get Address by Id ")
                .success(false)
                .build();
        try {
            response = employeeAddressDetailsService.selectEmployeeAddressDetailsByID(staff_id);
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
