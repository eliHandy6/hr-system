package com.metro.sfaffRegistration.biodata.spouse.api;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.spouse.dtos.SpouseDetailsDTO;
import com.metro.sfaffRegistration.biodata.spouse.services.SpouseDetailsService;
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
@RequestMapping("/staff/{staff_id}/spouse_details")
@Tag(name = "Spouse Details API")
public class SpouseDetailsAPI {
    private final SpouseDetailsService spouseDetailsService;

    public SpouseDetailsAPI(SpouseDetailsService spouseDetailsService) {
        this.spouseDetailsService = spouseDetailsService;
    }

    @Operation(summary = "Save Spouse Details in the database")
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
    public ResponseEntity<?> createSpouseDetails(@RequestBody @Valid SpouseDetailsDTO spouseDetailsDTO, @PathVariable("staff_id") Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Spouse Details.")
                .success(false)
                .data(spouseDetailsDTO)
                .build();
        try {
            response = spouseDetailsService.createSpouseDetails(spouseDetailsDTO, id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "update Spouse Details")
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
    @PutMapping({"/{spouse_id}"})
    public ResponseEntity<?> updateSpouseDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid SpouseDetailsDTO spouseDetailsDTO, @PathVariable("spouse_id") Long spouse_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update spouse details")
                .success(false)
                .data(spouseDetailsDTO)
                .build();
        try {
            response = spouseDetailsService.updateSpouseDetails(id, spouseDetailsDTO, spouse_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Operation(summary = "fetch spouse details based on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getSpouseDetailsByStaffId(@PathVariable("staff_id") Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get Spouse Details by Id ")
                .success(false)
                .build();
        try {
            response = spouseDetailsService.selectSpouseDetailsByID(id);
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
