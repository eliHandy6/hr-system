package com.metro.setups.staff.jobsubcategories.controllers;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.jobsubcategories.dtos.JobSubCategoryDTOS;
import com.metro.setups.staff.jobsubcategories.services.JobSubCategoryService;
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
@RequestMapping("/api/v1/jobsubcategory")
@Tag(name = "Job sub category")
public class JobSubCategoryControllers {
    private final JobSubCategoryService jobCategoryService;

    public JobSubCategoryControllers(JobSubCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }



    @Operation(description = "Create Job sub-Category")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = " Successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<?> createJobSubCategory(

            @RequestBody @Valid JobSubCategoryDTOS jobCategoryDTO
            ) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to create Job Sub-Category")
                .success(false)
                .data(jobCategoryDTO)
                .build();
        try {
            response = jobCategoryService.createJobSubCategory(jobCategoryDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmptySpaceExceptionHandler apiExceptions) {
            response.setMessage(apiExceptions.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (DuplicateResourceException duplicateResourceException){
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "update the Job Sub-Categories")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Job sub-category with given id not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobSubCategories(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid JobSubCategoryDTOS jobCategoryDTO
    ) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Job Sub-Category")
                .success(false)
                .data(jobCategoryDTO)
                .build();
        try {
            response = jobCategoryService.updateJobSubCategory(id, jobCategoryDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(ResourceNotFoundException resourceNotFoundException){
            response.setMessage(resourceNotFoundException.getMessage());
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Get all Job Sub-categories")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully retrieved all the Job Categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getAllJobSubCategories() {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get all the Job Sub-Categories")
                .success(false)
                .data(null)
                .build();
        try {
            response = jobCategoryService.getJobSubCategory();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "fetch job sub-category based on name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/search")
    public ResponseEntity<?> getJobSubCategoryByName(@RequestParam String name) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get job category ")
                .success(false)
                .build();
        try {
            response = jobCategoryService.selectJobSubCategoryByName(name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "get the job sub-category based on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "section not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobSubCategoryById(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get job sub-category")
                .success(false)
                .build();
        try {
            response = jobCategoryService.selectJobSubCategoryByID(id);
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
