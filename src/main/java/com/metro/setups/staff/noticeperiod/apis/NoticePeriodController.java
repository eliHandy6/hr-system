package com.metro.setups.staff.noticeperiod.apis;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.noticeperiod.dtos.NoticePeriodDto;
import com.metro.setups.staff.noticeperiod.services.NoticePeriodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/

@RestController
@RequestMapping("api/v1/notice-periods")
@Tag(name = "Notice Periods")
@RequiredArgsConstructor
@Slf4j
public class NoticePeriodController {


    private final NoticePeriodService noticePeriodService;

    @Operation(summary = "create notice period")
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
    public ResponseEntity<?> createNoticePeriod(@RequestBody @Valid NoticePeriodDto noticePeriodDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the notice period")
                .success(false)
                .data(noticePeriodDto)
                .build();
        try {
            response = noticePeriodService.createNoticePeriod(noticePeriodDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "update notice period")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "notice period not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateNoticePeriod(@PathVariable Long id, @RequestBody @Valid NoticePeriodDto noticePeriodDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update notice period")
                .success(false)
                .data(noticePeriodDto)
                .build();
        try {
            response = noticePeriodService.updateNoticePeriod(id, noticePeriodDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "get notice periods")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})

    @GetMapping
    public ResponseEntity<?> getAllNoticePeriods() {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the notice periods")
                .success(false)
                .build();
        try {
            response = noticePeriodService.getNoticePeriods();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch notice period on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "notice period not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticePeriodOnId(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get notice period")
                .success(false)
                .build();
        try {
            response = noticePeriodService.selectNoticePeriodById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "fetch notice period based on name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/search")
    public ResponseEntity<?> getNoticePeriodByName(@RequestParam String name) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get notice period ")
                .success(false)
                .build();
        try {
            response = noticePeriodService.selectNoticePeriodByName(name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
