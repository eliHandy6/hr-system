package com.metro.setups.department.controllers;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.department.dtos.DepartmentDTO;
import com.metro.setups.department.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/department")
@Tag(name = "department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(description = "Get all the available departments")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully retrieved all the available departments",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> getAllTitles() {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get all the titles")
                .success(false)
                .data(null)
                .build();
        try {
            response = departmentService.getAllDepartments();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(description = "Create a Department")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = " Successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> createDepartment(

            @RequestBody @Valid DepartmentDTO departmentDTO
            ) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to create Department")
                .success(false)
                .data(departmentDTO)
                .build();
        try {
            response = departmentService.createDepartment(departmentDTO);
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
    @GetMapping("/{id}")
    @Operation(description = "Get the department by Id")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully Retrieved the department by Id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> getTitleById(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get the department by Id")
                .success(false)
                .data(null)
                .build();
        try {
            response = departmentService.getDepartmentById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(description = "Get the department by name")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully searched for the department",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Department not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> fetchTitleByName(
            @RequestParam String name
    ) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get the Department with given name")
                .success(false)
                .data(null)
                .build();
        try {
            response = departmentService.getDepartmentByName(name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    @Operation(description = "update the Department")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> updateTitle(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid DepartmentDTO departmentDto
    ) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Department")
                .success(false)
                .data(departmentDto)
                .build();
        try {
            response = departmentService.updateDepartment(id, departmentDto);
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
