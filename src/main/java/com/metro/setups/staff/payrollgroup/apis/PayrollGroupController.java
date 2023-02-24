package com.metro.setups.staff.payrollgroup.apis;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.payrollgroup.dtos.PayrollGroupDto;
import com.metro.setups.staff.payrollgroup.services.PayrollGroupService;
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
@RequestMapping("api/v1/payroll-groups")
@Tag(name = "Payroll Groups")
@RequiredArgsConstructor
@Slf4j
public class PayrollGroupController {


    private final PayrollGroupService payrollGroupService;

    @Operation(summary = "create payroll group")
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
    public ResponseEntity<?> createPayrollGroup(@RequestBody @Valid PayrollGroupDto payrollGroupDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the payroll group")
                .success(false)
                .data(payrollGroupDto)
                .build();
        try {
            response = payrollGroupService.createPayrollGroup(payrollGroupDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "update payroll group")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "payroll group not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PutMapping({"/{id}"})
    public ResponseEntity<?> updatePayrollGroup(@PathVariable Long id, @RequestBody @Valid PayrollGroupDto payrollGroupDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the payroll group")
                .success(false)
                .data(payrollGroupDto)
                .build();
        try {
            response = payrollGroupService.updatePayrollGroup(id, payrollGroupDto);
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

    @Operation(summary = "get payroll groups")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})

    @GetMapping
    public ResponseEntity<?> getPayrollGroups() {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch payroll groups")
                .success(false)
                .build();
        try {
            response = payrollGroupService.getPayrollGroups();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch payroll group on id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "payroll group  not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getPayrollGroupOnId(@PathVariable Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get payroll group")
                .success(false)
                .build();
        try {
            response = payrollGroupService.selectPayrollGroupById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "fetch payroll  group based on name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping("/search")
    public ResponseEntity<?> getPayrollGroupByName(@RequestParam String group_name) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get payroll  group name ")
                .success(false)
                .build();
        try {
            response = payrollGroupService.selectPayrollGroupByName(group_name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
