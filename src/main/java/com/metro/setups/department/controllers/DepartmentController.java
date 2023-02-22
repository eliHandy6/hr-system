package com.metro.setups.department.controllers;

import com.metro.exceptions.ApiResponses;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
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
        ApiResponses response = ApiResponses.builder()
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
    @Operation(description = "Create a title")
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
    public ResponseEntity<?> createTitle(

            @RequestBody @Valid DepartmentDTO departmentDTO
            ) {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to create title")
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
}
