package com.metro.sfaffRegistration.leadership.api;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.leadership.data.LeadershipDetailsDTO;
import com.metro.sfaffRegistration.leadership.service.LeadershipDetailsService;
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
@RequestMapping("/staff/{staff_id}/leadership")
@Tag(name = "LEADERSHIP DETAILS")
public class LeadershipDetailsAPI {
    private final LeadershipDetailsService leadershipDetailsService;

    public LeadershipDetailsAPI(LeadershipDetailsService leadershipDetailsService) {
        this.leadershipDetailsService = leadershipDetailsService;
    }
    @Operation(summary = "Save Leadership Details.")
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
    public ResponseEntity<?> createLeadershipDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid LeadershipDetailsDTO leadershipDetailsDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Leadership Details.")
                .success(false)
                .data(leadershipDetailsDTO)
                .build();
        try {
            response = leadershipDetailsService.createLeadershipDetails(leadershipDetailsDTO, id);
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

    @Operation(summary = "update Leadership Details")
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
    @PutMapping("/{leadership_id}")
    public ResponseEntity<?> updateLeadershipDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid LeadershipDetailsDTO leadershipDetailsDTO, @PathVariable("leadership_id") Long leadership_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Leadership Details")
                .success(false)
                .data(leadershipDetailsDTO)
                .build();
        try {
            response = leadershipDetailsService.updateLeadershipDetails(id, leadershipDetailsDTO, leadership_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch  All Leadership details related to an employee")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getStaffLeadershipDetailsById(@PathVariable("staff_id") Long staff_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get leadership Details. ")
                .success(false)
                .build();
        try {
            response = leadershipDetailsService.selectLeadershipDetailsByID(staff_id);
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
