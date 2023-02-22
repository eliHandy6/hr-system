package com.metro.setups.common.ethnicity.apis;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.common.ethnicity.dtos.EthnicGroupDto;
import com.metro.setups.common.ethnicity.services.EthnicGroupService;
import com.metro.setups.staff.category.dtos.StaffCategoryDto;
import com.metro.setups.staff.category.services.StaffCategoryService;
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
@RequestMapping("api/v1/ethnicity")
@Tag(name = "Ethnicity")
@RequiredArgsConstructor
@Slf4j
public class EthnicGroupController {


    private final EthnicGroupService ethnicGroupService;

    @Operation(summary = "create ethnicity")
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
    public ResponseEntity<?> createEthnicity(@RequestBody @Valid EthnicGroupDto ethnicGroupDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the ethnicity")
                .success(false)
                .data(ethnicGroupDto)
                .build();
        try {
            response = ethnicGroupService.createEthnicGroup(ethnicGroupDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "update ethnicity")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ethnicity not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateEthnicity(@PathVariable Long id, @RequestBody @Valid EthnicGroupDto ethnicGroupDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the ethnicity")
                .success(false)
                .data(ethnicGroupDto)
                .build();
        try {
            response = ethnicGroupService.updateEthnicGroup(id, ethnicGroupDto);
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

    @Operation(summary = "get ethnic groups")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})

    @GetMapping
    public ResponseEntity<?> getAllEthnicGroups() {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the ethnic groups")
                .success(false)
                .build();
        try {
            response = ethnicGroupService.getEthnicGroups();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch ethnic group on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ethnic group  not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getEthnicGroupOnId(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get ethnic group")
                .success(false)
                .build();
        try {
            response = ethnicGroupService.selectEthnicGroupByID(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "fetch ethnic group based on name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/search")
    public ResponseEntity<?> getEthnicGroupByName(@RequestParam String group_name) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get ethnic group ")
                .success(false)
                .build();
        try {
            response = ethnicGroupService.selectEthnicGroupByName(group_name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
