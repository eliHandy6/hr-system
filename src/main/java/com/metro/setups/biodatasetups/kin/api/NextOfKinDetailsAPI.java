package com.metro.setups.biodatasetups.kin.api;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.biodatasetups.kin.dtos.NextOfKinDTO;
import com.metro.setups.biodatasetups.kin.services.NextOfKinService;
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
@RequestMapping("/api/v1/next_of_kin_details")
@Tag(name = "Next of Kin API")
public class NextOfKinDetailsAPI {
    private final NextOfKinService nextOfKinService;

    public NextOfKinDetailsAPI(NextOfKinService nextOfKinService) {
        this.nextOfKinService = nextOfKinService;
    }
    @Operation(summary = "Save next of kin in the database")
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
    public ResponseEntity<?> createNextOfKinDetails(@RequestBody @Valid NextOfKinDTO nextOfKinDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save next of Kin.")
                .success(false)
                .data(nextOfKinDTO)
                .build();
        try {
            response = nextOfKinService.createNextOfKinDetails(nextOfKinDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "update Details of the Next of Kin")
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
    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateNextOfKinDetails(@PathVariable Long id, @RequestBody @Valid NextOfKinDTO nextOfKinDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update next of kin details")
                .success(false)
                .data(nextOfKinDTO)
                .build();
        try {
            response = nextOfKinService.updateNextOfKinDetails(id, nextOfKinDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Operation(summary = "get All Next of Kins in the Hospital")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})

    @GetMapping
    public ResponseEntity<?> getNextOfKins() {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get All Next of Kins")
                .success(false)
                .build();
        try {
            response = nextOfKinService.getNextOfKinDetails();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Operation(summary = "fetch next of kin based on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getNextOfKinById(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get Next of Kin by Id ")
                .success(false)
                .build();
        try {
            response = nextOfKinService.selectNextOfKinDetailsByID(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "fetch Next of Kin based on name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/search")
    public ResponseEntity<?> getChildByName(@RequestParam String name) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get Next of Kin Details ")
                .success(false)
                .build();
        try {
            response = nextOfKinService.selectNextOfKinDetailsByName(name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
